package com.ehayes.ppmtool.Security;

import com.ehayes.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ehayes.ppmtool.Security.SecurityConstants.EXPIRATION_TIME;
import static com.ehayes.ppmtool.Security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    //Generate the token with valid username and password
    //make sure you have the authentication from spring security
    public String GenerateToken(Authentication authentication){
        User user = (User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        //EXPIRATION_TIME coming from another package look at import
        Date expired = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now)
                .setExpiration(expired).signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }

    //Validate the token
    public boolean validateToken(String token) {
        //this is going to validate the incoming token
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT Signature");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired Jwt token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported Jwt token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        //return false is just a invalid token
            return false;
    }

    //get the user id from the token use custom details service that we validate the right user based on token
    //when we set up filter we want to extract the user id from the claims
    public long getUseridFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
       String id = (String)claims.get("id");
       return Long.parseLong(id);
    }
}
