package com.pouya.javatest.Auth;

import com.pouya.library.Execptions.AuthException;
import com.pouya.javatest.Models.Service.Interfaces.TokenService;
import com.pouya.javatest.Models.User;
import com.pouya.library.StaticKeys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.pouya.library.Translations.MessageHelper._t;

@Component
public class LoginHttpTokenAuthFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    public LoginHttpTokenAuthFilter(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/") || path.startsWith("/api/public/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(StaticKeys.headerKey);

            if (header == null || !header.startsWith(StaticKeys.Bearer)) {
                throw new AuthException(_t("auth.invalid_token"));
            }
            String plainToken = header.substring(7).trim();
            User user = TokenValidator.validate(plainToken,tokenService);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (AuthException ex) {
            SecurityContextHolder.clearContext();
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

}