package com.example.observability.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final Counter requestCounter;

    public DemoController(MeterRegistry registry) {
        this.requestCounter = Counter.builder("demo.requests.total")
                .description("Total number of demo endpoint calls")
                .register(registry);
    }

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        requestCounter.increment();
        log.info("GET /api/hello called");
        return ResponseEntity.ok(Map.of("message", "Hello from Spring Boot Observability Template!"));
    }
}
