package com.iot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/smartPlantie", "/smartPlantie/signUp", "/smartPlantie/logIn", "/css/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/smartPlantie/logIn")
                        .loginProcessingUrl("/smartPlantie/logIn")
                        .defaultSuccessUrl("/smartPlantie/stats")
                )
                .logout(logout -> logout
                        .logoutUrl("/smartPlantie/logout")
                        .logoutSuccessUrl("/smartPlantie")
                )
                .build();

    }
}

