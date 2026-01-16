package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Tắt CSRF vì ta dùng API REST
                .authorizeExchange(exchange -> exchange
                        // Cho phép truy cập Swagger và Eureka (sau này) mà không cần login
                        .pathMatchers("/eureka/**", "/actuator/**").permitAll()
                        // Tất cả các request còn lại (ví dụ /api/products) BẮT BUỘC phải đăng nhập
                        .anyExchange().authenticated()
                )
                // Kích hoạt tính năng xác thực bằng OAuth2 Resource Server (JWT)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return serverHttpSecurity.build();
    }
}