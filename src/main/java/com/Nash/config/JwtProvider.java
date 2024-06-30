package com.Nash.config;

import com.Nash.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.regex.Pattern;


//building jwt

public class JwtProvider {

    private static final SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

//    token generate
    public static String generateToken(Authentication auth){

        User user = (User) auth.getPrincipal();

        String jwt = Jwts.builder()
                .setIssuer("Code with Nash")
                .setIssuedAt( new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",user.getEmail())
                .signWith(key)
                .compact();

//        finally our jet genrated

        return jwt;


    }

    // to fetch the email from token--decoding
//    public static String getEmailFromJwtToken(String jwt){
//        // jet tokem format is Bearer token ..but we have to remove Bearer
//        jwt=jwt.substring(7); // to skip Bearer
//
//
//        Claims claims =Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
////            Claims claims =Jwts.parser().verifyWith(key()).build().parserSignedClaims(jwt).getPayload().getSubject();
//
//
//        String email=String.valueOf(claims.get("email"));
//        System.out.println("Extracted email from token: " + email);
//
//        return email;
//    }
    public static String getEmailFromJwtToken(String jwt) {
        System.out.println("Processing JWT in getEmailFromJwtToken: " + jwt);

        try {
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }

            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String email = String.valueOf(claims.get("email"));

            System.out.println("Extracted email: " + email);

            // Add this check
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email in token: " + email);
            }

            return email;
        } catch (Exception e) {
            System.out.println("Error in getEmailFromJwtToken: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Add this method to validate email
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

}
