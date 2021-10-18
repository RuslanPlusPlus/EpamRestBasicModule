package com.epam.esm.dao;

import com.epam.esm.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenDao {
    Optional<RefreshToken> save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    void delete(long tokenId);
    Optional<RefreshToken> update(RefreshToken refreshToken);
    Optional<RefreshToken> findByUserId(Long userId);
}
