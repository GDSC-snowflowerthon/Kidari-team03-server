package com.committers.snowflowerthon.committersserver.auth.github;

import feign.Logger;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    // feign 로깅 처리
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // feign custom decoder
    Decoder feignDecoder() {
        return new GitHubDecoder();
    }
}
