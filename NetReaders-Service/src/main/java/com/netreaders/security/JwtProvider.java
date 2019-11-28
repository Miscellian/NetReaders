package com.netreaders.security;

import java.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.netreaders.service.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
    private final String jwtSecret = "jwtSuperMegaSecretKey";
    private final int jwtExpiration = 3600;

    public String generateJwtToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject((userPrincipal.getUsername()));
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000));
        claims.put("Authorities", userPrincipal.getAuthorities()
        		.stream().map(authority -> authority.getAuthority())
        		.collect(Collectors.toList()));

        return Jwts.builder()
        			.setClaims(claims)
        			.signWith(SignatureAlgorithm.HS512, jwtSecret)
		            .compact();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {} //TODO implement logger
//            logger.error("Invalid JWT signature -> Message: {} ", e);
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token -> Message: {}", e);
//        } catch (ExpiredJwtException e) {
//            logger.error("Expired JWT token -> Message: {}", e);
//        } catch (UnsupportedJwtException e) {
//            logger.error("Unsupported JWT token -> Message: {}", e);
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty -> Message: {}", e);
//        }
        
        return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
			                .parseClaimsJws(token)
			                .getBody().getSubject();
    }
    public List<String> getAuthoritiesFromToken(String token) {
    	if(token == null) return null;
    	token = token.substring(token.indexOf(' ') + 1);
    	Claims claims = Jwts.parser().setSigningKey(jwtSecret)
    			.parseClaimsJws(token).getBody();

    	return (List<String>) claims.get("Authorities");
    }
}
