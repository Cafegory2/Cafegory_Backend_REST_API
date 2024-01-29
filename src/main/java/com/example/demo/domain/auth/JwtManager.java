package com.example.demo.domain.auth;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
class JwtManager {
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.type}")
	private String type;
	private JwtBuilder jwtBuilder;

	JwtManager() {
		this.jwtBuilder = Jwts.builder();
		jwtBuilder.header().type(type);
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

	public Map<String, Object> decode(String jwtString) {
		JwtParserBuilder parser = Jwts.parser();
		Jws<Claims> claimsJws = parser.verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
			.build()
			.parseSignedClaims(jwtString);
		return claimsJws.getPayload();
	}
}
