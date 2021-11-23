package com.epam.esm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    private static final String CLAIMS_ROLE_ATTRIBUTE = "roles";

    private String secretKey;

    private long tokenLifeTime;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtProvider(@Qualifier("userServiceImpl") UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        tokenLifeTime = JwtProperties.TOKEN_EXPIRE_LENGTH_MIN;
        secretKey = Base64.getEncoder().encodeToString(JwtProperties.SECRET_KEY.getBytes());
    }

    public String generateToken(String username){
        Claims claims = Jwts.claims().setSubject(username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        claims.put(CLAIMS_ROLE_ATTRIBUTE, roles);
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(tokenLifeTime)
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public long getTokenLifeTime() {
        return tokenLifeTime;
    }
}
