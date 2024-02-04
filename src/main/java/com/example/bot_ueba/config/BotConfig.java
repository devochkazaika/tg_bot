package com.example.bot_ueba.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Data;

@Configuration
@EnableScheduling
@Data
@PropertySource("application.properties")
public class BotConfig {


    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;
}
