//package com.progetto.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	private final String[] WHITE_LIST_URLS = { "/spring/client/save", "/home" , "/spring/client/findByEmail/**", "/spring/client/export-to-excel"};
//
//	private final String[] WHITE_LIST_URLS2 = { "/spring/client/getAll" , "/render"};
// 
//	@Bean
//	public InMemoryUserDetailsManager userDetailsManager() {
//		UserDetails admin = User.withUsername("admin")
//		        .password(encoder().encode("Accedi1$"))
//		        .roles("ADMIN")
//		        .build();
//		    UserDetails user = User.withUsername("user")
//		        .password(encoder().encode("Accedi1$"))
//		        .roles("USER")
//		        .build();
//		    
//		    return new InMemoryUserDetailsManager(admin, user);
//	}
//
//	@Bean
//	public PasswordEncoder encoder() {
//	    return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		http.cors().and().csrf().disable()
//				.authorizeHttpRequests(authorize -> authorize.requestMatchers(WHITE_LIST_URLS).anonymous()
//						.requestMatchers(WHITE_LIST_URLS2).hasAuthority("ROLE_ADMIN").anyRequest().authenticated()).formLogin().loginProcessingUrl("/render").and()
//				.httpBasic();
//		return http.build();
////		
////		http.csrf()
////        .disable()
////        .authorizeRequests()
////        .requestMatchers(WHITE_LIST_URLS)
////        .permitAll()
////        .anyRequest()
////        .authenticated() 
////        .and()
////        .formLogin()
////        .loginProcessingUrl("/render")
////        .defaultSuccessUrl("/home", true)
////        .failureUrl("/error")
////        .and()
////        .logout()
////        .deleteCookies("JSESSIONID");
//
////		http.csrf().and().cors().disable().authorizeHttpRequests(
////				x -> x.requestMatchers(HttpMethod.POST, WHITE_LIST_URLS).permitAll().anyRequest().authenticated())
////				.formLogin();
//	}
//
//	
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//}