package cn.sysu.sse.recruitment.job_platform_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

	private final JwtTokenService tokenService;
	private final CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtTokenService tokenService, CustomUserDetailsService userDetailsService) {
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = authHeader.substring(7);
		String email;
		try {
			email = tokenService.getClaim(token, claims -> claims.getSubject());
		} catch (Exception e) {
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
			}
		}
		filterChain.doFilter(request, response);
	}
}


