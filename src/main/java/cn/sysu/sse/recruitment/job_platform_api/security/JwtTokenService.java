package cn.sysu.sse.recruitment.job_platform_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {

	@Value("${security.jwt.secret}") // base64 for dev
	private String secret;

	@Value("${security.jwt.expiration-ms}")
	private long expirationMs;

	public String generateToken(String subject, Map<String, Object> claims) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + expirationMs);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, String subject) {
		String sub = getClaim(token, Claims::getSubject);
		return sub.equals(subject) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		Date exp = getClaim(token, Claims::getExpiration);
		return exp.before(new Date());
	}

	public <T> T getClaim(String token, Function<Claims, T> resolver) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return resolver.apply(claims);
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}


