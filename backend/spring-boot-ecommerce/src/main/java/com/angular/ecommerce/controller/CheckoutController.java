package com.angular.ecommerce.controller;

import com.angular.ecommerce.dto.PaymentInfo;
import com.angular.ecommerce.dto.Purchase;
import com.angular.ecommerce.dto.PurchaseResponse;
import com.angular.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService){//todo un inject
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){

        var purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo)throws StripeException{

        logger.info("paymentInfo.amount: " + paymentInfo.getAmount());
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

        var paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
