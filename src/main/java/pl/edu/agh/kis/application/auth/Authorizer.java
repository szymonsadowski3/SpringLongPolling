package pl.edu.agh.kis.application.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

/**
 * Static class used to authorize users of the application. It is based on JWT - JSON Web Token
 */
public class Authorizer {
    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256("secret");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param token JSON Wen Token
     * @param issuer Issuer - String passed in when token was generated
     * @return result of verification
     */
    public static boolean verifyToken(String token, String issuer) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return issuer.equals(jwt.getIssuer());
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    /**
     * @param issuer String, which is basically a passphrase for algorithm
     * @return generated JSON Web Token
     */
    public static String generateToken(String issuer) {
        String token = JWT.create()
                .withIssuer(issuer)
                .sign(algorithm);

        return token;
    }
}
