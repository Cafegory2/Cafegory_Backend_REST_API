package com.example.demo.domain.auth;

import static com.example.demo.exception.ExceptionType.*;

import java.util.Date;

import com.example.demo.exception.CafegoryException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtManager {
	private final String secret;
	private JwtBuilder jwtBuilder;

	public JwtManager(String secret) {
		this.secret = secret;
		this.jwtBuilder = Jwts.builder();
		jwtBuilder.header().type("JWT");
	}

	void claim(String key, Object value) {
		jwtBuilder.claim(key, value);
	}

	void setLife(Date issuedAt, int lifeTimeAsSeconds) {
		jwtBuilder
			.issuedAt(issuedAt)
			.expiration(Date.from(issuedAt.toInstant().plusSeconds(lifeTimeAsSeconds)));
	}

	String make() {
		String jwt = jwtBuilder
			.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
			.compact();
		jwtBuilder = Jwts.builder();
		return jwt;
	}

	public Claims decode(String jwtString) {
		JwtParserBuilder parser = Jwts.parser();
		try {
			Jws<Claims> claimsJws = parser.verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
				.build()
				.parseSignedClaims(jwtString);
			return claimsJws.getPayload();

		} catch (ExpiredJwtException e) {
			throw new CafegoryException(JWT_EXPIRED);
		} catch (JwtException e) {
			throw new CafegoryException(JWT_DESTROYED);
		}
	}

	public boolean isExpired(String jwtString) {
		String errMessage = "";
		try {
			decode(jwtString);
		} catch (CafegoryException e) {
			errMessage = e.getMessage();
		}
		return errMessage.equals(JWT_EXPIRED.getErrorMessage());
	}
}
