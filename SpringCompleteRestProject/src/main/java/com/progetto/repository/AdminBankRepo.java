package com.progetto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progetto.domain.AdminBank;

@Repository
public interface AdminBankRepo extends JpaRepository<AdminBank, String>{
 
	
	
   
   
}
