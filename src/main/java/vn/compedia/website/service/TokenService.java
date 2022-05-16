package vn.compedia.website.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import vn.compedia.website.model.Account;
import vn.compedia.website.util.PropertiesUtil;
import vn.compedia.website.util.Tokens;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenService {
    public String createToken(Account account, long expiresIn, String key) {
        Claims claims = Jwts.claims();

        claims.setSubject(account.getUsername());
        claims.put("fullName", account.getFullName());
        claims.put("email", account.getEmail());
        Date now = new Date();
        Date expirationDate = new Date(expiresIn);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Tokens createToken(Account account) {
        Tokens token = new Tokens();
        long expiresIn = (Long.parseLong(PropertiesUtil.getProperty("jwt.accessTokenValidityInMilliseconds")) / 1000);

        token.setAccessToken(createAccessToken(account));
        token.setExpiresIn(LocalDateTime.now().plusSeconds(expiresIn));

        return token;
    }

    private String createAccessToken(Account account) {
        long expiresIn = expiration(Long.parseLong(PropertiesUtil.getProperty("jwt.accessTokenValidityInMilliseconds")));

        return createToken(account, expiresIn, PropertiesUtil.getProperty("jwt.accessTokenSecretKey"));
    }

    private long expiration(long validity) {
        Date now = new Date();
        return now.getTime() + validity;
    }
}
