package org.itevents.service.security;

import io.jsonwebtoken.*;
import org.itevents.service.CryptTokenService;
import org.itevents.service.exception.CryptTokenServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ramax on 2/23/16.
 */
@Service
public class JWTCryptTokenService implements CryptTokenService {

    @Value("${jwt.key}")
    private String key;

    public JWTCryptTokenService(String key) {
        this.key = key;
    }

    public JWTCryptTokenService() {
    }

    @Override
    public String encrypt(Token token) {
        return Jwts.builder()
                .claim("username", token.getUsername())
                .claim("role", token.getRole())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public Token decrypt(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            String username = (String) claims.get("username");
            String role = (String) claims.get("role");

            return new Token(username, role);
        } catch (SignatureException | MalformedJwtException e) {
            throw new CryptTokenServiceException("Don't trust the JWT");
        }
    }
}
