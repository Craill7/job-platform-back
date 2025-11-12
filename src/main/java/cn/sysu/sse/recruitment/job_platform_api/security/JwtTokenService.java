package cn.sysu.sse.recruitment.job_platform_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT token服务
 */
@Service
public class JwtTokenService {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

	@Value("${security.jwt.secret}") // base64 for dev
	private String secret;

	@Value("${security.jwt.expiration-ms}")
	private long expirationMs;

	/**
	 * 生成JWT token
	 * @param subject 主题（用户邮箱）
	 * @param claims 自定义声明
	 * @return JWT token字符串
	 */
	public String generateToken(String subject, Map<String, Object> claims) {
		logger.info("开始生成JWT token，主题：{}", subject);
		Date now = new Date();
		Date exp = new Date(now.getTime() + expirationMs);
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
		logger.info("JWT token生成成功，主题：{}，过期时间：{}", subject, exp);
		return token;
	}

	/**
	 * 验证token是否有效
	 * @param token JWT token
	 * @param subject 期望的主题（用户邮箱）
	 * @return token是否有效
	 */
	public boolean isTokenValid(String token, String subject) {
		logger.debug("验证token有效性，主题：{}", subject);
		String sub = getClaim(token, Claims::getSubject);
		boolean isValid = sub.equals(subject) && !isTokenExpired(token);
		logger.debug("token验证结果：{}，主题：{}", isValid, subject);
		return isValid;
	}

	/**
	 * 检查token是否过期
	 * @param token JWT token
	 * @return token是否过期
	 */
	public boolean isTokenExpired(String token) {
		Date exp = getClaim(token, Claims::getExpiration);
		boolean isExpired = exp.before(new Date());
		if (isExpired) {
			logger.warn("token已过期，过期时间：{}", exp);
		}
		return isExpired;
	}

	/**
	 * 从token中获取指定声明
	 * @param token JWT token
	 * @param resolver 声明解析器
	 * @param <T> 声明类型
	 * @return 声明值
	 */
	public <T> T getClaim(String token, Function<Claims, T> resolver) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return resolver.apply(claims);
	}

	/**
	 * 获取签名密钥
	 * @return 签名密钥
	 */
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}


