package com.app.paymentmockserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MockPaymentRequest {
    private Long paymentId;
    private int totalPrice;
}
