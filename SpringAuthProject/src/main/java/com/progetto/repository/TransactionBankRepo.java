package com.progetto.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progetto.domain.Transaction;
import com.progetto.utils.Constants;

import jakarta.persistence.EntityNotFoundException;

@Repository
public interface TransactionBankRepo extends JpaRepository<Transaction, String>{
 

    @Query(nativeQuery = true, value = Constants.FIND_TRANSACTION_BY_BANK_ACOOUNT_ID )
    public List<Transaction> findTransactionByBankAccounId(String id) throws EntityNotFoundException;
	
    @Query(nativeQuery = true, value = "select * from transaction_client tc where tc.atm_code = ? and tc.transaction_time = ?")
    public Transaction findByAtmCodeAndTimeStamp(String atmCode, Timestamp time) throws EntityNotFoundException;
	
    @Query(nativeQuery = true, value = "select * from transaction_client tc where tc.status = ?")
    public List<Transaction> findByStatus(String status) throws EntityNotFoundException;
    
}
