package com.gayathri.projects;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//A configuration class telss spring that this class provides security rules and beans
@Configuration
public class SecurityConfig {
// registers this method's return value as spring bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable OAuth2 login
                .oauth2Login(Customizer.withDefaults())

                // Enable OAuth2 resource server (JWT validation)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                // Authorize requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/actuator/**").permitAll()
                        .requestMatchers("/api/crud/**").hasRole("USER")   // Only users with ROLE_USER can access CRUD
                        .requestMatchers("/admin/**").hasRole("ADMIN")     // Admin-only endpoints
                        .anyRequest().authenticated()
                )

                // CSRF disabled for APIs (enable if you use forms)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
