package com.althen.Ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AltenEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltenEcommerceApplication.class, args);
		System.err.println(new BCryptPasswordEncoder().encode("123456"));
	}

}
