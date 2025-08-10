package com.app.paymentmockserver;

import com.app.paymentmockserver.dto.AMockPaymentResponse;
import com.app.paymentmockserver.dto.MockPaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mock-payment-api")
public class MockPaymentApiController {

    private final WebhookService webhookService; // 웹훅 전송을 위한 서비스

    @PostMapping("/a-pay")
    public void processAPay(@RequestBody MockPaymentRequest request) {

        webhookService.sendAWebhook("A_PAY", request);
    }

    @PostMapping("/b-pay")
    public void processBPay(@RequestBody MockPaymentRequest request) {
        webhookService.sendBWebhook("B_PAY", request);
    }

    @PostMapping("/c-pay")
    public void processCPay(@RequestBody MockPaymentRequest request) {
        webhookService.sendCWebhook("C_PAY", request);
    }


}
