package com.committers.snowflowerthon.committersserver.auth.config;


import com.committers.snowflowerthon.committersserver.auth.jwt.JwtExceptionFilter;
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler successHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    private String[] permitList={
            "/login/oauth2/code/github",
            "/api/auth/**",
            "/api/auth/login", // 로그인 시 Jwt Filter를 거쳐버림 안 거치게 수정
            "/api/auth/redirect", // 로그인 시 Jwt Filter를 거쳐버림 안 거치게 수정
            "/api/auth/accessToken", //새로운 토큰 발급
            "/api/logout", // 로그아웃
            "/",
            "/health", // elb healthCheck
            "/redirect"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests
                            .requestMatchers(permitList).permitAll()
                            .anyRequest().hasAnyAuthority("ROLE_MEMBER");
                })

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // jwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class) // JwtExceptionFilter를 JwtFilter 앞에 추가

                .logout(log -> log
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )
                // OAuth 2.0 로그인 설정 시작
                .oauth2Login( (oauth2Login) -> oauth2Login
                        .authorizationEndpoint(authorization -> authorization // 인증 엔드포인트 설정
                                .baseUri("/login/oauth2/code/github") // 사용자가 로그인하려고 할 때 리다이렉션되는 기본 URI

                        ) // 커스텀 로그인 페이지가 필요하지 않으므로, 로그인 시 리다이렉션이 필요없다.
                        .redirectionEndpoint( redirection -> redirection
                                .baseUri("/login") // OAuth 2.0 공급자로부터 코드가 리다이렉션될 때의 기본 URI
//
                        ) // 리다이렉션 엔드포인트 설정
                        .successHandler(successHandler) // OAuth 2.0 로그인 성공 시의 핸들러를 설정
                        .userInfoEndpoint((endpoint)->endpoint
                                .userService(customOAuth2UserService)) // 사용자 정보 엔드포인트에서 사용할 사용자 서비스를 설정
                );

        return http.build();
    }

    public class CorsConfigurer extends AbstractHttpConfigurer<CorsConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .addFilter(corsConfig.corsFilter());
        }
    }
}