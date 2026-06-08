package com.mechbattle.server;

import com.mechbattle.server.config.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    private final AppProperties appProperties;

    public HealthController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "cors", appProperties.getCorsOrigins());
    }
}
