package com.progetto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progetto.domain.VerificationToken;
import com.progetto.utils.Constants;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, String>{
 
	
	 @Query(nativeQuery = true, value = Constants.FIND_BY_TOKEN_VERIFICATION)
	    public VerificationToken findVerificationByToken(String token) throws EntityNotFoundException;

    
    
}
