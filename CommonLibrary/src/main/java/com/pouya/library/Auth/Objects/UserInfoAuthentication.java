package com.pouya.library.Auth.Objects;


import com.pouya.library.DTO.UserInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserInfoAuthentication extends AbstractAuthenticationToken {

    private final UserInfo userInfo;
    private final String token;

    public UserInfoAuthentication(UserInfo userInfo, String token) {
        super(null);
        this.userInfo = userInfo;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userInfo;
    }
}