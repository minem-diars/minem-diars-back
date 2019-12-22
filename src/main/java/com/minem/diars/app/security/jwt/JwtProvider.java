package com.minem.diars.app.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.minem.diars.app.security.service.UserPrinciple;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {
	
	@Value("${minem.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${minem.app.jwtExpiration}")
	private int jwtExpiration;
	
	public String generateJwtToken(Authentication authentication) {
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
		
		return Jwts.builder()
					.setSubject(userPrincipal.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
	}
	
	public boolean validateJwtToken(String atuthToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(atuthToken);
			return true;
		} catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJws(token)
					.getBody().getSubject();
	}

}
