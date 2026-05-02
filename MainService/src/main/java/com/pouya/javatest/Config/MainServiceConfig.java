package com.pouya.javatest.Config;


import com.pouya.javatest.Auth.CustomUserDetailsService;
import com.pouya.javatest.Auth.LoginHttpTokenAuthFilter;
import com.pouya.javatest.GRpc.Middleware.AuthServerGrpcInterceptor;
import com.pouya.javatest.Models.Service.Interfaces.TokenService;
import com.pouya.library.Auth.HttpSecurityConfig;
import com.pouya.library.InitConfig;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Import(InitConfig.class)
public class MainServiceConfig {

    @Autowired
    private LoginHttpTokenAuthFilter tokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http = HttpSecurityConfig.securityFilterChain(http);
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
    @GrpcGlobalServerInterceptor
    public ServerInterceptor authGrpcInterceptor(TokenService tokenService) {
        return new AuthServerGrpcInterceptor(tokenService);
    }
}