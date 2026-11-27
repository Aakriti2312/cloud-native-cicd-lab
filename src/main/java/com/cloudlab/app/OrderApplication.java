package com.cloudlab.app;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @GetMapping("/api/v1/status")
    public Map<String, String> getStatus() {
        String version = System.getenv().getOrDefault("APP_VERSION", "1.0.0-local");
        return Map.of("service", "order-service", "status", "OPERATIONAL", "version", version);
    }

    @GetMapping("/healthz")
    public Map<String, String> healthz() {
        return Map.of("probe", "READY");
    }
}
