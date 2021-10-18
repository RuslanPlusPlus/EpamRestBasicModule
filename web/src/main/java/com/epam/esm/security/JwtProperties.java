package com.epam.esm.security;

public final class JwtProperties {
    public static final String SECRET_KEY = "rus_ned";
    public static final long TOKEN_EXPIRE_LENGTH_MIN = 10;
    public static final long REFRESH_TOKEN_EXPIRE_LENGTH_MIN = 1440;

    private JwtProperties(){}
}
