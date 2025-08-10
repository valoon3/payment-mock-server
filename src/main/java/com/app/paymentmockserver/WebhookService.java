package com.app.paymentmockserver;

import com.app.paymentmockserver.dto.MockPaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${main-app.webhook-url}")
    private String mainAppWebhookUrl;

    public static int aMockPaymentId = 1;
    public static int bMockPaymentId = 1;
    public static int cMockPaymentId = 1;

    public record AWebhookPayload(int transactionId, long paymentId, String status, int totalPrice, String aMockInformation1, String aMockInformation2) {}
    public record BWebhookPayload(int transactionId, long paymentId, String status, int totalPrice, String bMockInformation1, String bMockInformation2) {}
    public record CWebhookPayload(int transactionId, long paymentId, String status, int totalPrice, String cMockInformation1, String cMockInformation2) {}

    @Async
    public void sendAWebhook(String provider, MockPaymentRequest request) {
        String url = mainAppWebhookUrl + "/" + provider;
        try {
            sendWebhook(provider, url, new AWebhookPayload(aMockPaymentId ++, request.getPaymentId(), "SUCCESS", request.getTotalPrice(), "aMockInfo1", "aMockInfo2"));
        } catch (Exception e) {
            sendWebhook(provider, url, new AWebhookPayload(aMockPaymentId ++, request.getPaymentId(), "FAIL", request.getTotalPrice(), "aMockInfo1", "aMockInfo2"));
        }
    }

    @Async
    public void sendBWebhook(String provider, MockPaymentRequest request) {
        String url = mainAppWebhookUrl + "/" + provider;
        try {
            sendWebhook(provider, url, new BWebhookPayload(bMockPaymentId ++, request.getPaymentId(), "SUCCESS", request.getTotalPrice(), "bMockInfo1", "bMockInfo2"));
        } catch (Exception e) {
            sendWebhook(provider, url, new BWebhookPayload(bMockPaymentId ++, request.getPaymentId(), "FAIL", request.getTotalPrice(), "bMockInfo1", "bMockInfo2"));
            log.error("Webhook 전송 실패", e);
        }
    }

    @Async
    public void sendCWebhook(String provider, MockPaymentRequest request) {
        String url = mainAppWebhookUrl + "/" + provider;
        try {
            sendWebhook(provider, url, new CWebhookPayload(bMockPaymentId ++, request.getPaymentId(), "SUCCESS", request.getTotalPrice(), "cMockInfo1", "cMockInfo2"));
        } catch (Exception e) {
            sendWebhook(provider, url, new CWebhookPayload(cMockPaymentId ++, request.getPaymentId(), "FAIL", request.getTotalPrice(), "cMockInfo1", "cMockInfo2"));
            log.error("Webhook 전송 실패", e);
        }
    }

    private void sendWebhook(String provider, String url, Object payload) {
        log.info("[Webhook] '{}' (으)로 최종 결제 결과 전송. URL: {}, Payload: {}", provider, url, payload);

        restTemplate.postForEntity(url, payload, String.class);
    }
}
