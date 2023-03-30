package com.progetto.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progetto.domain.AdminBank;
import com.progetto.domain.ClientBank;
import com.progetto.repository.AdminBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.utils.Constants;

import jakarta.transaction.Transactional;

@Service
public class UserCredentialService implements UserDetailsService {

	@Autowired
	ClientBankRepo clientRepo;

	@Autowired
	AdminBankRepo adminRepo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.startsWith(Constants.ADMIN_CODE_STARTING)) {
			AdminBank admin = adminRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException(
					String.format("ADMIN WITH THIS %s NOT PRESENT IN THE SYSTEM", username)));
			logger.info("THE ADMIN LOGGING: {}", admin.toString());
			return new UserCredentials(admin.getId(), admin.getPassword(), Constants.ADMIN_ROLE);
		} else {
			ClientBank client = Optional.ofNullable(clientRepo.findByEmail(username))
					.orElseThrow(() -> new UsernameNotFoundException(
							String.format("EMAIL %s , NOT PRESENT IN THE SYSTEM", username)));
			logger.info("THE CLIENT LOGGING: {}", client.toString());
			return new UserCredentials(client.getEmail(), client.getPassword(), Constants.USER_ROLE);
		}

	}
	
	
	

}
