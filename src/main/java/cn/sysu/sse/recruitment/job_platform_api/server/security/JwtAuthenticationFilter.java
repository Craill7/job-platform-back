package cn.sysu.sse.recruitment.job_platform_api.server.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtTokenService tokenService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestURI = request.getRequestURI();
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		logger.info("Processing request to: {} with auth header: {}", requestURI, authHeader != null ? "present" : "absent");
		if (authHeader != null) {
			if (authHeader.length() > 20) {
				logger.info("Auth header preview: {}...", authHeader.substring(0, Math.min(20, authHeader.length())));
			} else {
				logger.warn("Auth header is too short: {}", authHeader);
			}
		}

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			logger.debug("No valid Bearer token found, continuing filter chain");
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7).trim();
		
		// 验证 token 格式（JWT 应该包含两个点）
		if (token.isEmpty()) {
			logger.warn("Empty token after 'Bearer ' prefix");
			filterChain.doFilter(request, response);
			return;
		}
		
		if (token.split("\\.").length != 3) {
			logger.warn("Invalid JWT token format. Token should contain exactly 2 period characters. Token length: {}, first 20 chars: {}", 
					token.length(), token.length() > 20 ? token.substring(0, 20) + "..." : token);
			filterChain.doFilter(request, response);
			return;
		}
		
		String email;
		try {
			email = tokenService.getClaim(token, claims -> claims.getSubject());
			logger.debug("Extracted email from token: {}", email);
		} catch (Exception e) {
			logger.warn("Invalid token, continuing filter chain. Error: {}", e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			if (tokenService.isTokenValid(token, email)) {
				// 检查用户账户是否启用
				if (!userDetails.isEnabled()) {
					logger.warn("User account is disabled or not active: {}, status: {}", 
							email, userDetails instanceof cn.sysu.sse.recruitment.job_platform_api.server.security.CustomUserDetails 
									? ((cn.sysu.sse.recruitment.job_platform_api.server.security.CustomUserDetails) userDetails).getUser().getStatus() 
									: "unknown");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.getWriter().write("{\"code\":403,\"message\":\"账户未激活或已被禁用\"}");
					response.setContentType("application/json");
					return;
				}
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				logger.info("Successfully authenticated user: {} for request: {}", email, requestURI);
			} else {
				logger.warn("Token is invalid for user: {} on request: {}", email, requestURI);
			}
		} else {
			if (email == null) {
				logger.warn("Email is null after token extraction for request: {}", requestURI);
			} else {
				logger.debug("Security context already contains authentication for request: {}", requestURI);
			}
		}

		filterChain.doFilter(request, response);
	}
}