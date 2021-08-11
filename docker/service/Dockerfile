# 来源镜像
FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Shanghai
ARG JAR_NAME=system-service.jar
ADD ${JAR_NAME} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
