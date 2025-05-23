package com.almardev.rpgAPI.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    //Si la variable no existe, se usará un valor por defecto (SOLO PARA DESARROLLO LOCAL)
    //En producción (Railway), SIEMPRE se proporcionará la variable de entorno.
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY") != null ?
            System.getenv("JWT_SECRET_KEY") :
            "CLAVE_SECRETA_POR_DEFECTO_SOLO_PARA_LOCAL_NO_USAR_EN_PROD_MIN_32_CHARS"; //
    private static final long EXPIRATION_TIME = 86400000; // 24 horas

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String validateTokenAndGetUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}