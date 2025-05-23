package com.almardev.rpgAPI.config;

import com.almardev.rpgAPI.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable());
        // .authorizeHttpRequests(authorize -> authorize // COMENTA ESTAS LÍNEAS
        //         .requestMatchers("/auth/**").permitAll() // COMENTA ESTAS LÍNEAS
        //         .anyRequest().authenticated() // COMENTA ESTAS LÍNEAS
        // ) // COMENTA ESTAS LÍNEAS
        // .sessionManagement(session -> session // COMENTA ESTAS LÍNEAS
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // COMENTA ESTAS LÍNEAS
        // ); // COMENTA ESTAS LÍNEAS

        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // COMENTA ESTA LÍNEA

        return http.build();
    }
}