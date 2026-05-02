package com.pouya.orders;

import com.pouya.common.grpc.AuthServiceGrpc;
import com.pouya.library.Auth.GRpc.AuthClient;
import com.pouya.library.Auth.GRpc.AuthInterceptor;
import com.pouya.library.Auth.HttpSecurityConfig;
import com.pouya.library.InitConfig;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Import(InitConfig.class)
@ComponentScan(basePackageClasses = {AuthClient.class})
public class ServiceConfig {

    @Bean
    public AuthInterceptor authInterceptor(AuthClient authClient) {
        return new AuthInterceptor(authClient);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthInterceptor authInterceptor) throws Exception {
        http = HttpSecurityConfig.securityFilterChain(http);
        http.addFilterBefore(authInterceptor, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}