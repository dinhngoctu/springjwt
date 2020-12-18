package com.viettel.kttb.gateway.CheckInfoPhone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class CheckInfoPhoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckInfoPhoneApplication.class, args);
	}

}
