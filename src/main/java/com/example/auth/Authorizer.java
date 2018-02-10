package com.example.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public class Authorizer {
    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256("secret");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyToken(String token, String issuer) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return issuer.equals(jwt.getIssuer());
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    public static String generateToken(String issuer) {
        String token = JWT.create()
                .withIssuer(issuer)
                .sign(algorithm);

        return token;
    }
}
