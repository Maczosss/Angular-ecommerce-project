package com.angular.ecommerce.dto;

import com.angular.ecommerce.entity.Address;
import com.angular.ecommerce.entity.Customer;
import com.angular.ecommerce.entity.Order;
import com.angular.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
