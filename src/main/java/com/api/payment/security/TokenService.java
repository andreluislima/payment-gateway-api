package com.api.payment.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.payment.domain.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

@Service
public class TokenService {

	 @Value("${api.security.token.secret}")
	    private String secret;
	 
	 public String generateToken(Usuario user){
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(secret);

	            String token = JWT.create()
	                    .withIssuer("login-auth-api")
	                    .withSubject(user.getCpf())
	                    .withExpiresAt(this.generateExpirationDate())
	                    .sign(algorithm);
	            return token;
	        } catch (JWTCreationException exception){
	            throw new RuntimeException("Error while authenticating");
	        }
	    }
	 
	 public String validateToken(String token){
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(secret);
	            return JWT.require(algorithm)
	                    .withIssuer("login-auth-api")
	                    .build()
	                    .verify(token)
	                    .getSubject();
	        } catch (JWTVerificationException exception) {
	            return null;
	        }
	    }
	 
	 private Instant generateExpirationDate(){
	        return LocalDateTime.now()
	        		.plusMinutes(3).toInstant(ZoneOffset.of("-03:00"));
	    }
//	 
//	 .plusMinutes(30) // expira em 30 minutos
//	 .plusHours(6)    // expira em 6 horas
//	 .plusDays(1)  
}
