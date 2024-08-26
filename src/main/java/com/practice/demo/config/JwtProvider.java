package com.practice.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    //private final String SECRET_KEY= JwtConstants.SECRET_KEY;
    private final Long EXPIRATION_TIME = JwtConstants.EXPIRATION_TIME;
    private final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private Key getSignInKey() {
        return SIGNING_KEY;
    }

//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

//    public String generateToken(Authentication authentication) {
//
//        String username = authentication.getName();
//
//        String jwt= Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
//                .compact();
//
//        System.out.println("JWT: "+jwt);
//        return jwt;
//    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }

//    public String generateToken(User user) {
//
//        String jwt= Jwts.builder()
//                .setSubject(user.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
//                .compact();
//
//        System.out.println("JWT: "+jwt);
//        return jwt;
//    }

    public String generateToken(Map<String, Object> claims, String subject) {

        String jwt= Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSignInKey()) //,SignatureAlgorithm.HS512)
                .compact();

        System.out.println("JWT: "+jwt);
        return jwt;
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails user) {
        final String email = extractEmail(token);
        //return (email.equals(user.getEmail()) && !isTokenExpired(token));
        return email.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
