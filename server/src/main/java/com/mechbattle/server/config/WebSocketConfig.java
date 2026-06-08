package com.mechbattle.server.config;

import com.mechbattle.server.websocket.GameWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameWebSocketHandler gameWebSocketHandler;
    private final AppProperties appProperties;

    public WebSocketConfig(GameWebSocketHandler gameWebSocketHandler, AppProperties appProperties) {
        this.gameWebSocketHandler = gameWebSocketHandler;
        this.appProperties = appProperties;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        var registration = registry.addHandler(gameWebSocketHandler, "/ws");
        if (appProperties.usesOriginPatterns()) {
            registration.setAllowedOriginPatterns(appProperties.corsPatternsArray());
        } else {
            registration.setAllowedOrigins(appProperties.corsOriginsArray());
        }
    }
}
