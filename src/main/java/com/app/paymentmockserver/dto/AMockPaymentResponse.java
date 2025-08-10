package com.app.paymentmockserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AMockPaymentResponse {
    private Long paymentId;
    private String paymentStatus;
    private int totalPrice;
    private String aMockInformation1;
    private String aMockInformation2;
}
