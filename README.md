# payment-mock-server

결제사(PG) 역할을 흉내 내는 Mock 서버입니다. 주 애플리케이션이 결제 요청을 보내면, 실제 결제사처럼 비동기로 결제 결과 webhook을 되돌려 줍니다. [meeting-room-reservations](https://github.com/valoon3/meeting-room-reservations)의 결제 연동을 로컬에서 재현하려고 만들었습니다.

## 동작

1. 주 애플리케이션이 `POST /api/v1/mock-payment-api/{a-pay|b-pay|c-pay}` 로 `paymentId`, `totalPrice` 를 보냅니다.
2. 서버는 요청을 바로 받아들이고, `@Async` 로 분리된 스레드에서 주 애플리케이션의 webhook URL(`/api/v1/webhooks/payments/{provider}`)로 결제 결과를 POST 합니다.
3. webhook 페이로드에는 결제사별 거래 ID, 주 애플리케이션의 `paymentId`, 상태(`SUCCESS`/`FAIL`), 금액이 담깁니다.

결제사를 A, B, C 세 종류로 둔 이유는 결제사마다 응답 필드가 다른 상황을 주 애플리케이션 쪽에서 어떻게 흡수하는지 테스트하기 위해서입니다.

## 스택

- Java 21, Spring Boot (spring-boot-starter-web)
- 멀티 스테이지 Dockerfile (의존성 레이어 캐시 분리)

## 실행

단독 실행:

```bash
./gradlew bootRun
# 8081 포트. webhook 대상 URL은 src/main/resources/application.yml 의 main-app.webhook-url
```

meeting-room-reservations의 `docker-compose up` 으로 함께 띄우면 `app` 컨테이너를 webhook 대상으로 사용합니다.

## 한계

- 거래 ID는 메모리 카운터라 재시작하면 1부터 다시 시작합니다.
- 결제 실패를 의도적으로 발생시키는 옵션은 없습니다. webhook 전송 자체가 실패했을 때만 `FAIL` 을 보냅니다.
