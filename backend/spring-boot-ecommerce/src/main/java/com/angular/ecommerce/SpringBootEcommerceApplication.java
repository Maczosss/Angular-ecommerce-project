package com.angular.ecommerce;

import com.angular.ecommerce.dao.ProductRepository;
import com.angular.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.angular.ecommerce.dao")
public class SpringBootEcommerceApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootEcommerceApplication.class, args);
	}
}


