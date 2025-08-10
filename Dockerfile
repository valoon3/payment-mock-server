# --- 1. 빌드 단계 ---
FROM openjdk:21-jdk-slim as builder
WORKDIR /app

# 1단계: Gradle 관련 파일만 먼저 복사
# (이 파일들이 변경되지 않으면, 다음 단계는 캐시를 사용하게 됩니다)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2단계: 의존성만 먼저 다운로드 (가장 시간이 오래 걸리는 부분)
# 이 명령어로 의존성을 미리 받아두면, 소스 코드만 변경되었을 때는 이 단계를 건너뛰어 빌드 속도가 매우 빨라집니다.
RUN ./gradlew dependencies

# 3단계: 전체 소스 코드 복사
COPY src src

# 4단계: 애플리케이션 빌드
RUN ./gradlew bootJar

# --- 2. 실행 단계 ---
FROM openjdk:21-jdk-slim
WORKDIR /app

# 빌드 단계에서 생성된 최종 JAR 파일만 복사
COPY --from=builder /app/build/libs/*.jar ./application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]