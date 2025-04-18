package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.config;

import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.security.JwtAuthenticatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticatorFilter jwtAuthenticatorFilter;

    public SecurityConfig(JwtAuthenticatorFilter jwtAuthenticatorFilter) {
        this.jwtAuthenticatorFilter = jwtAuthenticatorFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/card/**").permitAll()
                        .requestMatchers("/transaction/new").permitAll()
                        .anyRequest().authenticated()
                )
                //.addFilterBefore(jwtAuthenticatorFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
