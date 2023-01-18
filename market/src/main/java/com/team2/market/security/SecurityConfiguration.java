package com.team2.market.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team2.market.util.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	@Bean
	public JwtVerificationFilter jwtVerificationFilter() {
		return new JwtVerificationFilter(jwtUtil, userDetailsService);
	}

	@Bean
	public JwtEntryPoint jwtEntryPoint() {
		return new JwtEntryPoint();
	}

	@Bean
	public WebSecurityCustomizer securityCustomizer() {
		return web -> web.ignoring().antMatchers("/h2-console/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtVerificationFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint());

		http // 추가 적인 api 및 권한 설정 필요
			.authorizeHttpRequests(auth -> auth
				.antMatchers("/api/signup,/api/login").permitAll()
				.antMatchers("/api/users/profile").authenticated()
				.antMatchers("/api/seller/profile").hasRole("SELLER")
				.antMatchers("/api/auth").hasRole("ADMIN")
			);

		return http.build();
	}
}
