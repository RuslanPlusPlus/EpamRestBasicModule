package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
public class JwtProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";

    private String secretKey;
    private long tokenLifeTime;

    private UserDetailsService userDetailsService;

    @Autowired
    public JwtProvider(@Qualifier("userServiceImpl") UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        tokenLifeTime = JwtProperties.TOKEN_EXPIRE_LENGTH_MIN;
        secretKey = Base64.getEncoder().encodeToString(JwtProperties.SECRET_KEY.getBytes());
    }

    /*
    public String createToken(String username){

    }

     */

    public long getTokenLifeTime() {
        return tokenLifeTime;
    }
}
