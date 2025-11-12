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

		logger.debug("Processing request to: {} with auth header: {}", requestURI, authHeader != null ? "present" : "absent");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			logger.debug("No valid Bearer token found, continuing filter chain");
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);
		String email;
		try {
			email = tokenService.getClaim(token, claims -> claims.getSubject());
			logger.debug("Extracted email from token: {}", email);
		} catch (Exception e) {
			logger.debug("Invalid token, continuing filter chain", e);
			filterChain.doFilter(request, response);
			return;
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			if (tokenService.isTokenValid(token, email)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				logger.debug("Successfully authenticated user: {}", email);
			} else {
				logger.debug("Token is invalid for user: {}", email);
			}
		} else {
			logger.debug("Security context already contains authentication or email is null");
		}

		filterChain.doFilter(request, response);
	}
}