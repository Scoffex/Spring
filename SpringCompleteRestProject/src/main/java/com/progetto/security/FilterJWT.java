package com.progetto.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterJWT extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JWTokenService jwtService;

	@Autowired
	UserCredentialService userCredentialService;
	String token;
	String username;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("FILTER JWT ACTIVE...");
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null &&   authHeader.startsWith("Bearer ")){
			 token = authHeader.substring(authHeader.indexOf(" ")+1);
			 username = jwtService.extractUsername(token);
		}
		
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userCredentialService.loadUserByUsername(username);
				if(jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
			filterChain.doFilter(request, response);
	}

}
