package com.dentalcare.g5.main.service.auth.impl;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.service.auth.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    // 24 HORAS
    private static final long JWT_EXPIRATION_MS = 1000 * 60 * 60 * 24;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private static final long RENEWAL_THRESHOLD_MS = 1000 * 60 * 30;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(UsuarioDto userData) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userData.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                // Corrección: Usar SignatureAlgorithm.HS256 con hmacShaKeyFor
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Cambiado de ES256 a HS256
                .compact();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Boolean validateToken(String token, UsuarioDto userData) {
        final String username = extractUsername(token);
        return (username.equals(userData.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenNearingExpiration(String jwt) {
        final Date expirationDate = extractExpiration(jwt);
        long timeLeft = expirationDate.getTime() - System.currentTimeMillis();
        return  timeLeft > 0 && timeLeft < RENEWAL_THRESHOLD_MS;
    }
}
    