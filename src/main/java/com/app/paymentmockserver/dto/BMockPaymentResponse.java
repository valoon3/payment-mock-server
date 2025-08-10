package com.app.paymentmockserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BMockPaymentResponse {
    private Long paymentId;
    private String paymentStatus;
    private int totalPrice;
    private String bMockInformation1;
    private String bMockInformation2;
}
