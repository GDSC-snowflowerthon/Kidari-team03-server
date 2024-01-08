package com.committers.snowflowerthon.committersserver.auth.config;

/*
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtAuthenticationFilter;
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtProvider;
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final JwtProvider tokenService;
    private String[] permitList={
            "/**",
            "/api/login", //로그인시 Jwt Filter를 거치지 않도록
            "/home?accessToken=*", //리다이렉트
    };
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    //private final RefreshRepository refreshRepository;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http, JwtUtils jwtUtils) throws Exception {
        http
                .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/api/**").permitAll()
                //여기는 권한 즉 user,admin 등등 모든 처리를 하겠다는 걸
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(jwtUtils),
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/login")
                .and()
                .redirectionEndpoint()
                .baseUri("/api/auth/code")
                .and()
                .successHandler(successHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils) {
        return new JwtAuthenticationFilter(jwtUtils);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {  //해당 URL은 필터 거치지 않겠다
        return (web -> web.ignoring().antMatchers(permitList)); //여기는 모든 필터를 거치지 않는 곳
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://s3-ap-northeast-2.amazonaws.com", "https://github.com", "http://52.78.80.150:9000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Key", "Authorization", "access-control-allow-origin", "Authorizationsecret", "accessToken", "refreshToken"));
        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}
 */

import com.committers.snowflowerthon.committersserver.auth.jwt.JwtExceptionFilter;
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler successHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    private String[] permitList={
            "/api/auth/login", //로그인시 Jwt Filter를 거쳐버림 안 거치게 수정
            "/api/auth/accessToken", //새로운 토큰 발급
            "/api/auth/logout", // 로그아웃
            "/"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 보호 비활성화
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() // 폼 기반 로그인 비활성화
                .httpBasic().disable() // HTTP 기본 인증 비활성화
                .apply(new CorsConfigurer()) // CorsConfigurer 적용 (CORS 관련 설정을 위한 클래스)
                .and()
                .authorizeRequests()
                .requestMatchers(permitList).permitAll()
                .anyRequest().access("hasRole('ROLE_MEMBER')") // 그 외의 모든 요청에 대해 멤버 권한에만 허가
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // jwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class) // JwtExceptionFilter를 JwtFilter 앞에 추가
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .and()
                // OAuth 2.0 로그인 설정 시작
                .oauth2Login()
                    .authorizationEndpoint() // 인증 엔드포인트 설정
                    .baseUri("/api/auth") // 사용자가 로그인하려고 할 때 리다이렉션되는 기본 URI
                .and()
                    .redirectionEndpoint() // 리다이렉션 엔드포인트 설정
                    .baseUri("/api/auth/login") // OAuth 2.0 공급자로부터 코드가 리다이렉션될 때의 기본 URI
                .and()
                .successHandler(successHandler) // OAuth 2.0 로그인 성공 시의 핸들러를 설정
                .userInfoEndpoint() // 사용자 정보 엔드포인트 설정
                .userService(customOAuth2UserService); // 사용자 정보 엔드포인트에서 사용할 사용자 서비스를 설정

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