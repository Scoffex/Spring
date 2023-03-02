package com.progetto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progetto.domain.VerificationToken;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Integer>{
 

    
    
}
