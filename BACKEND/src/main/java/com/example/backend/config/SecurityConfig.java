package com.example.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Thằng này mốt nghiên cứu sau, nghe nói chống việc bị người khác lợi dụng
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers(
                                        "/api/register",
                                        "/api/login",
                                        "/api/products",
                                        "/api/product-types",
                                        "/api/product-detail/{productId}",
                                        "/api/search/{keyword}")
                                .permitAll()
                                .anyRequest().authenticated());

        http.addFilterAfter(jwtRequestFilter, BasicAuthenticationFilter.class); // Cái này là thêm filter sau khi chạy qua thằng lìn trên
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // Thằng này encode password, thằng security bắt làm!!
        return new BCryptPasswordEncoder();
    }
}
