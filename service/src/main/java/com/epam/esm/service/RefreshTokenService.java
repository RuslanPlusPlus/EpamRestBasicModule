package com.epam.esm.service;

import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;

public interface RefreshTokenService {
    RefreshTokenDto createRefreshToken(String username);
    RefreshTokenDto findByToken(String token);
    RefreshTokenDto updateToken(RefreshTokenDto tokenDto);
    void validateToken(RefreshTokenDto token, UserDto userDto);
}

