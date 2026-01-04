package com.techChirayu.Auth.Auth_Application.security;

import com.techChirayu.Auth.Auth_Application.entity.Role;
import com.techChirayu.Auth.Auth_Application.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service

public class JwtService {
    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issure;

    public JwtService(
       @Value("${jwt.secret-key}")String secret,
       @Value("${jwt.access-token-expiration-seconds}") long accessTtlSeconds,
       @Value("${jwt.refresh-token-expiration-seconds}") long refreshTtlSeconds,
       @Value("${jwt.issuer-name}") String issure) {

        if(secret==null || secret.length()<64){
            throw  new IllegalArgumentException("Invalid secret");
        }

        this.key= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issure = issure;
    }

    //generate token:
    public String genrateAccessToken(User user){
        Instant now = Instant.now();
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .issuer(issure)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claims(Map.of(
                        "email",user.getEmail(),
                        "roles",roles,
                        "typ","access"
                ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //refresh Token.
    public String genrateRefreshToken(User user,String jti){
        Instant now = Instant.now();
        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .issuer(issure)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ","refresh")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //parse the token

    public Jws<Claims> parse(String token){
        try {
            return  Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isAccessToken(String token){
        Claims c = parse(token).getPayload();
        return "access".equals(c.get("typ"));
    }

    public boolean isRefreshToken(String token){
        Claims c = parse(token).getPayload();
        return "access".equals(c.get("typ"));
    }

    public UUID getUserId(String token){
        Claims c = parse(token).getPayload();
        return UUID.fromString(c.getSubject());
    }

    public String getjti(String token){
        return  parse(token).getPayload().getId();
    }


}
