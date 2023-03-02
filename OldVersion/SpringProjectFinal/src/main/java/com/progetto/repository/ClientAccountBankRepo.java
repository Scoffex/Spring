package com.progetto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progetto.domain.BankAccount;
import com.progetto.utils.Constants;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface ClientAccountBankRepo extends JpaRepository<BankAccount, Integer>{
 
	@Query(nativeQuery = true, value=Constants.FIND_BY_CLIENT_ID)
    public List<BankAccount> findByClientId(int id) throws EntityNotFoundException;
	
	@Query(nativeQuery = true, value="SELECT * FROM bank_account_client c where c.iban = ?")
    public BankAccount findByIban(String iban) throws EntityNotFoundException;
    
}
