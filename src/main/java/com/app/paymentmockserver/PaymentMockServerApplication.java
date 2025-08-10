package com.app.paymentmockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PaymentMockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMockServerApplication.class, args);
    }

}
