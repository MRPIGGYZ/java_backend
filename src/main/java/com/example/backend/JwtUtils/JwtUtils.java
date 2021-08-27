package com.example.backend.JwtUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    public static String createToken(String userId, String userName, String passWord) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.HOUR,60);
        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .withClaim("userName", userName)
                .sign(Algorithm.HMAC256(passWord+"HelloBackend"));
    }

    public static boolean verifyToken(String token, String passWord) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(passWord+"HelloBackend")).build();
            jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getAudience(String token) {
        String userId = null;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return "token error";
        }
        System.out.println(userId);
        return userId;
    }


    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}