package com.taskmanager.service;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

@Service
public class JwtService {

        private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey12345";

        private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24;

        public String generateToken(String email) {

                return Jwts.builder()

                                .setSubject(email)

                                .setIssuedAt(new Date())

                                .setExpiration(
                                                new Date(System.currentTimeMillis()
                                                                + JWT_EXPIRATION))

                                .signWith(getSignInKey(),
                                                SignatureAlgorithm.HS256)

                                .compact();
        }

        private Key getSignInKey() {

                return Keys.hmacShaKeyFor(
                                SECRET_KEY.getBytes());

        }

        public String extractEmail(String token) {

                return extractClaim(token, Claims::getSubject);

        }

        public <T> T extractClaim(
                        String token,
                        Function<Claims, T> claimsResolver) {

                final Claims claims = extractAllClaims(token);

                return claimsResolver.apply(claims);

        }

        private Claims extractAllClaims(String token) {

                return Jwts.parserBuilder()

                                .setSigningKey(getSignInKey())

                                .build()

                                .parseClaimsJws(token)

                                .getBody();

        }

        private boolean isTokenExpired(String token) {

                return extractExpiration(token)
                                .before(new Date());

        }

        private Date extractExpiration(String token) {

                return extractClaim(token, Claims::getExpiration);

        }

        public boolean isTokenValid(
                        String token,
                        UserDetails userDetails) {

                final String email = extractEmail(token);

                return email.equals(userDetails.getUsername())

                                && !isTokenExpired(token);

        }
}