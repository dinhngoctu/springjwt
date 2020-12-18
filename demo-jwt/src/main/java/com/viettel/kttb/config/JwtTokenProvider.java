package com.viettel.kttb.config;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "asdasdkasdaasda";
    private final long JWT_EXPIRATION = 604800000L;

    public String generateToken(String userName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, this.getJWT_SECRET())
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.getJWT_SECRET())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private String getJWT_SECRET() {
        StringBuilder builder = new StringBuilder();
        builder.append(JWT_SECRET);
        return builder.toString();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.getJWT_SECRET()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
