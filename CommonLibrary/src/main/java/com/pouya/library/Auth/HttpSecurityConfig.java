package com.pouya.library.Auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class HttpSecurityConfig {
    public static HttpSecurity securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","api/public/**").permitAll()
                        .requestMatchers("/api/user/**","/api/orders/**").authenticated()
                        .anyRequest().denyAll()
                );
    }
}
