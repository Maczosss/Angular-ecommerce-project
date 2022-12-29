package com.angular.ecommerce.service;

import com.angular.ecommerce.dao.CustomerRepository;
import com.angular.ecommerce.dto.PaymentInfo;
import com.angular.ecommerce.dto.Purchase;
import com.angular.ecommerce.dto.PurchaseResponse;
import com.angular.ecommerce.entity.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        //initialize Stripe API with secret key
        Stripe.apiKey = secretKey;

    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //retrive the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        var orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with orderItems
        var orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        //populate order with billing add and shippadd
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //populate customer with order
        var customer = purchase.getCustomer();

        //check if this is an existing customer
        var theEmail = customer.getEmail();
        var customerFromDb = customerRepository.findByEmail(theEmail);
        if (customerFromDb != null) {
            customer = customerFromDb;
        }
        customer.add(order);

        //save to the db
        customerRepository.save(customer);
        //return a response

        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Purchase from online shop, test message");

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {

        //generate a random UUID number (UUID ver 4)

        return UUID.randomUUID().toString();
    }
}
