package com.gayathri.projects.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public OpaqueTokenIntrospector introspector() {
        return new NimbusOpaqueTokenIntrospector(
                "https://github.com/login/oauth/access_token",
                "your-client-id",
                "your-client-secret"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/employees/**").hasRole("USER") // ✅ modern DSL
                        .anyRequest().authenticated()
                )
                .oauth2Login() // ✅ no more `.and()`
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.opaqueToken()); // ✅ modern resource server config

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}