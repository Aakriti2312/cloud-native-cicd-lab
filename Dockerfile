# Stage 1: Dependency Cache & Compilation
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /workspace

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# Stage 2: Minimal Hardened Production Runtime
FROM eclipse-temurin:17-jre-jammy AS runtime

WORKDIR /app

RUN groupadd --system appgroup && useradd --system --gid appgroup --no-create-home appuser

COPY --from=builder /workspace/target/order-service.jar /app/order-service.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/order-service.jar"]
