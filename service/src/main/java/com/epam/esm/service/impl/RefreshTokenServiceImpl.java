package com.epam.esm.service.impl;

import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.RefreshTokenDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.RefreshToken;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.RefreshTokenMapper;
import com.epam.esm.mapper.impl.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements com.epam.esm.service.RefreshTokenService {

    private Long refreshTokenLifeTime;

    private final UserDao userDao;

    private final RefreshTokenDao refreshTokenDao;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenMapper tokenMapper;

    private final UserMapper userMapper;

    @Autowired
    public RefreshTokenServiceImpl(UserDao userDao,
                                   RefreshTokenDao refreshTokenDao,
                                   PasswordEncoder passwordEncoder,
                                   RefreshTokenMapper tokenMapper,
                                   UserMapper userMapper
    ) {

        //refreshTokenLifeTime = JwtProperties.REFRESH_TOKEN_EXPIRE_LENGTH_MIN;
        this.userDao = userDao;
        this.refreshTokenDao = refreshTokenDao;
        this.passwordEncoder = passwordEncoder;
        this.tokenMapper = tokenMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public RefreshTokenDto createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();

        Optional<User> user = userDao.findByName(username);
        Optional<RefreshToken> existingToken = refreshTokenDao.findByUserId(user.get().getId());

        if (existingToken.isPresent()) {
            refreshToken = existingToken.get();
        } else {
            refreshToken.setUser(user.get());

            LocalDateTime exp = LocalDateTime.from(LocalDateTime.now().plusMinutes(refreshTokenLifeTime)
                    .atZone(ZoneId.systemDefault()).toInstant());
            refreshToken.setExpireDate(exp);

            refreshToken.setToken(UUID.randomUUID().toString());

            Optional<RefreshToken> refreshTokenOptional = refreshTokenDao.save(refreshToken);
        }
        return tokenMapper.mapEntityToDto(refreshToken);
    }

    @Override
    @Transactional
    public RefreshTokenDto findByToken(String token) {
        return null;
    }

    @Override
    @Transactional
    public RefreshTokenDto updateToken(RefreshTokenDto tokenDto) {
        final LocalDateTime exp = LocalDateTime.from(LocalDateTime.now().plusMinutes(refreshTokenLifeTime)
                .atZone(ZoneId.systemDefault()).toInstant());
        tokenDto.setExpireDate(exp);

        final String newToken = UUID.randomUUID().toString();
        tokenDto.setToken(newToken);

        refreshTokenDao.update(tokenMapper.mapDtoToEntity(tokenDto));
        return findByToken(newToken);
    }

    @Override
    @Transactional
    public void validateToken(RefreshTokenDto tokenDto, UserDto userDto) {
        validateUsersToken(tokenDto.getToken(), userDto);
        deleteTokenIfExpires(tokenDto);
    }

    private void validateUsersToken(String token, UserDto userDto) {

        Optional<User> userFromStorage = userDao.findByName(userDto.getUsername());

        String codedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(codedPassword);

        RefreshTokenDto refreshTokenDto = findByToken(token);
        UserDto tokenUser = refreshTokenDto.getUser();
        if (!tokenUser.equals(userMapper.mapEntityToDto(userFromStorage.get()))) {
            // TODO: 18.10.2021 throw exception
        }
    }

    private void deleteTokenIfExpires(RefreshTokenDto token) {
        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            refreshTokenDao.delete(token.getId());
            // TODO: 18.10.2021 throw exception

        }
    }
}
