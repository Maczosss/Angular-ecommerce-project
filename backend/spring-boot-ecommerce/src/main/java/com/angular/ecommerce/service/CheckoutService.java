package com.angular.ecommerce.service;

import com.angular.ecommerce.dto.PaymentInfo;
import com.angular.ecommerce.dto.Purchase;
import com.angular.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
