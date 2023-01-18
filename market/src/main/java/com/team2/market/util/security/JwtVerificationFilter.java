package com.team2.market.util.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.util.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = jwtUtil.resolveToken(request);

		if (token != null) {
			if (!jwtUtil.validateToken(token)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유요하지 않은 토큰입니다.");
			}

			Claims claims = jwtUtil.getUserInfoFromToken(token);
			UserDetails user = userDetailsService.loadUserByUsername(claims.getSubject());
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}
