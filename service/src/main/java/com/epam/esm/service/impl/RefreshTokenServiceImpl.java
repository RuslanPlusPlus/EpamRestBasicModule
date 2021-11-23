package com.epam.esm.service.impl;

import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.RefreshTokenService;

public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Override
    public RefreshTokenDto createRefreshToken(String username) {
        return null;
    }

    @Override
    public RefreshTokenDto findByToken(String token) {
        return null;
    }

    @Override
    public RefreshTokenDto updateToken(RefreshTokenDto tokenDto) {
        return null;
    }

    @Override
    public void validateToken(RefreshTokenDto token, UserDto userDto) {

    }
}
