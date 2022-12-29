package com.angular.ecommerce.dao;

import com.angular.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
}
