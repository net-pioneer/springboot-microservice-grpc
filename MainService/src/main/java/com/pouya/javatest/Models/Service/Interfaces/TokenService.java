package com.pouya.javatest.Models.Service.Interfaces;

import com.pouya.javatest.Models.PersonalAccessToken;
import com.pouya.javatest.Models.User;

import java.util.Optional;

public interface TokenService {
    public Optional<PersonalAccessToken> findByToken(String token);
    public void delete(PersonalAccessToken token);
    public void delete(String token);
    public Optional<PersonalAccessToken> getUserToken(Long userId);
    PersonalAccessToken createOrUpdateToken(User user);
}
