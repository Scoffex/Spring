package com.progetto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	FilterJWT jwtFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		 return http.httpBasic().and().csrf().disable().authorizeHttpRequests(x ->
//		 x.requestMatchers("/spring/client/save",
//		 "/spring/jwt/auth").permitAll().requestMatchers("/spring/admin/**").hasAuthority("ROLE_ADMIN").anyRequest().authenticated()).formLogin().and().build();
//		 
		 
		 
		return http.csrf().disable()
				.authorizeHttpRequests().requestMatchers("/spring/client/save", "/spring/jwt/auth", "/spring/verification/**").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/spring/admin/**").hasAuthority("ROLE_ADMIN").anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();

	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/spring/client/save", "/spring/admin/save", "/spring/jwt/auth",
				"/error");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setPasswordEncoder(passwordEncoder());
		auth.setUserDetailsService(userDetailsService());
		auth.setHideUserNotFoundExceptions(false);
		return auth;

	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserCredentialService();
	}

	@Bean
	public AuthenticationManager authenticateManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}