# Base image 선택
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/SSAFYnity_B-0.0.1-SNAPSHOT.jar app.jar

# 포트 설정 (스프링 부트 기본 포트는 8080)
EXPOSE 8080

# 실행 명령어 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
