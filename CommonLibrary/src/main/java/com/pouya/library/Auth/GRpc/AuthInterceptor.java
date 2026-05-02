package com.pouya.library.Auth.GRpc;

import com.pouya.common.grpc.UserResponse;
import com.pouya.library.Auth.Objects.UserInfoAuthentication;
import com.pouya.library.DTO.UserInfo;
import com.pouya.library.StaticKeys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
public class AuthInterceptor extends OncePerRequestFilter {

    private final AuthClient authClient;

    public AuthInterceptor(AuthClient authClient) {
        this.authClient = authClient;
    }
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(StaticKeys.headerKey);
        if (header != null && header.startsWith(StaticKeys.Bearer)) {
            String token = header.substring(7);
            try {
                UserResponse user = authClient.validate(token);
                UserInfo userInfo = new UserInfo(user.getId(),user.getUsername(),user.getRolesList());
                UserInfoAuthentication auth = new UserInfoAuthentication(userInfo, token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                handlerExceptionResolver.resolveException(request, response, null, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}