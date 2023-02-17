package com.progetto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progetto.domain.ClientBank;
import com.progetto.utils.Constants;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface ClientBankRepo extends JpaRepository<ClientBank, Integer>{
 
	
	
    @Query(nativeQuery = true, value = Constants.FIND_BY_EMAIL )
    public ClientBank findByEmail(String email) throws EntityNotFoundException;
    
    @Query(nativeQuery = true, value=Constants.FIND_BY_NAME)
    public List<ClientBank> findByName(String name);
    
    @Query(nativeQuery = true, value = Constants.FIND_BY_PAN_CODE)
    public ClientBank findByPanCode(String panCode);
    
   
}
