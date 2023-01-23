package com.team2.market.util.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtVerificationFilter jwtVerificationFilter() {
		return new JwtVerificationFilter(jwtUtil, userDetailsService);
	}

	@Bean CustomAccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
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
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint())
								.accessDeniedHandler(accessDeniedHandler());

		http.authorizeRequests(auth -> auth
			.antMatchers("/api/signup,/api/login").permitAll()
			.antMatchers("/api/users/profile").authenticated()
			.antMatchers("/api/sellers/profile").hasRole("SELLER")
			// 판매글 관리 권한
			.antMatchers(HttpMethod.POST, "/api/posts").access("hasRole('SELLER')")
			.antMatchers(HttpMethod.PUT, "/api/posts/**").access("hasRole('SELLER') or hasRole('ADMIN')")
			.antMatchers(HttpMethod.DELETE, "/api/posts/**").access("hasRole('SELLER') or hasRole('ADMIN')")
			// 관리자 권한
			.antMatchers("/api/auth/admin/**").access("hasRole('ADMIN')")
			
		);
		
		http.logout()
				.logoutUrl("/api/logout")
				.logoutSuccessUrl("/api/login");

		return http.build();
	}

}