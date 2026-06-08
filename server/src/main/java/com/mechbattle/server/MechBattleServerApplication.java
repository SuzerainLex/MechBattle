package com.mechbattle.server;

import com.mechbattle.server.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MechBattleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MechBattleServerApplication.class, args);
    }
}
