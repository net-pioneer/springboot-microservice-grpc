package com.pouya.javatest.Models.Service;

import com.pouya.javatest.Models.PersonalAccessToken;
import com.pouya.javatest.Models.Repository.ApiTokenRepository;
import com.pouya.javatest.Models.Service.Interfaces.TokenService;
import com.pouya.javatest.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    ApiTokenRepository _repo;

    @Override
    public Optional<PersonalAccessToken> findByToken(String token) {
        return _repo.findByToken(token.trim());
    }

    @Override
    public void delete(PersonalAccessToken token) {
        _repo.delete(token);
    }

    @Override
    public void delete(String token) {
        _repo.deleteByToken(token);
    }

    @Override
    public Optional<PersonalAccessToken> getUserToken(Long userId) {
        return _repo.findValidToken(userId, LocalDateTime.now());
    }

    @Override
    public PersonalAccessToken createOrUpdateToken(User user) {
        String tokenHash = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        PersonalAccessToken token = getUserToken(user.getId()).orElse(new PersonalAccessToken());
        token.setUser(user);
        token.setToken(tokenHash);
        token.setExpiresAt(LocalDateTime.now().plusDays(30));

        return _repo.save(token);
    }
}
