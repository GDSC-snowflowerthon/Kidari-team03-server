package com.committers.snowflowerthon.committersserver.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*"); // 모든 ip에 응답 허용
//        config.addAllowedHeader("*"); // 모든 헤더에 응답 허용
//        config.addAllowedMethod("*"); // 모든 http 메소드에 요청 허용
//        source.registerCorsConfiguration("/api/**", config);

        config.addAllowedOrigin("https://kidari.site");
        config.addAllowedOrigin("https://api.github.com"); // 깃허브 API 도메인
        config.addAllowedOrigin("https://github.com"); // 깃허브 인증 센터 도메인

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
