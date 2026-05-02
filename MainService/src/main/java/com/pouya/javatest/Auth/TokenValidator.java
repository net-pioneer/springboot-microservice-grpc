package com.pouya.javatest.Auth;

import com.pouya.javatest.Models.PersonalAccessToken;
import com.pouya.javatest.Models.Service.Interfaces.TokenService;
import com.pouya.javatest.Models.User;

import java.time.LocalDateTime;


import com.pouya.library.Execptions.AuthException;

public class TokenValidator {

    public static User validate(String token, TokenService tokenService) {

        if (token == null || token.isBlank()) {
            throw new AuthException("invalid_token");
        }
        PersonalAccessToken accessToken = tokenService.findByToken(token).orElseThrow(() -> new AuthException("invalid_token"));

        if (accessToken.getExpiresAt() != null && accessToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            tokenService.delete(accessToken);
            throw new AuthException("need_relogin");
        }
        return accessToken.getUser();
    }
}
