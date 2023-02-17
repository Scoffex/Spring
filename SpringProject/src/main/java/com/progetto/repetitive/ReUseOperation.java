package com.progetto.repetitive;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.domain.Transaction;
import com.progetto.exception.GenericError;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.utils.Constants;

@Component
public class ReUseOperation {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TransactionBankRepo transactionRepo;

	@Scheduled(fixedRate = 300000, initialDelay = 60000)
	@Transactional(rollbackFor = GenericError.class)
	public void myMethod() throws GenericError {
		try {
			logger.info("REVERSAL CHECK STARTING...");
			List<Transaction> transactions = transactionRepo.findByStatus(Constants.ANOMAL);
			if (transactions.isEmpty()) {
				String message = (new StringBuilder("TRANSACTION WITH STATUS ").append(Constants.ANOMAL.toUpperCase())
						.append(" NOT FOUND").toString());
				logger.info(message);
			} else {
				transactions.forEach(x -> x.setStatus(Constants.REVERSAL));
			}

		} catch (Exception e) {
			throw new GenericError(new StringBuilder("ERROR IN REVERSAL METHOD. ERROR ").append(e).toString());
		}
		logger.info("REVERSAL CHECK ENDING...");
	}

	
	
}
