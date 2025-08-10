package com.app.paymentmockserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CMockPaymentResponse {
    private Long paymentId;
    private String paymentStatus;
    private int totalPrice;
    private String cMockInformation1;
    private String cMockInformation2;
}
