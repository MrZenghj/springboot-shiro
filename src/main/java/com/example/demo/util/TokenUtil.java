package com.example.demo.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

public class TokenUtil {
    @Value("${token.secret}")
    private static String secret = "snipe233";

    /**
     *  根据tokenDetail生成 token
     * */
    public static String generateToken(Map<String,Object> claims,Date expirationDate) throws Exception {
        if(expirationDate == null){
            return Jwts.builder().addClaims(claims).signWith(SignatureAlgorithm.HS512,secret.getBytes("UTF-8")).compact();
        }else {
            return Jwts.builder().addClaims(claims).setExpiration(expirationDate).
                signWith(SignatureAlgorithm.HS512,secret.getBytes("UTF-8")).compact();
        }
    }

    public static Claims getClaimsFromToken(String token) throws Exception {
        return Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
    }

}