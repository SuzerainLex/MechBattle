package com.mechbattle.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String corsOrigins = "http://localhost:5173,http://127.0.0.1:5173";

    public String getCorsOrigins() {
        return corsOrigins;
    }

    public void setCorsOrigins(String corsOrigins) {
        this.corsOrigins = corsOrigins;
    }

    public String[] corsOriginsArray() {
        return java.util.Arrays.stream(corsOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    public boolean allowAllOrigins() {
        return "*".equals(corsOrigins.trim());
    }

    public boolean usesOriginPatterns() {
        if (allowAllOrigins()) {
            return true;
        }
        for (String origin : corsOriginsArray()) {
            if (origin.contains("*")) {
                return true;
            }
        }
        return false;
    }

    public String[] corsPatternsArray() {
        return allowAllOrigins() ? new String[] { "*" } : corsOriginsArray();
    }
}
