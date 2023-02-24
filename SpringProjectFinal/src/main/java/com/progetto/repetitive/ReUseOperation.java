package com.progetto.repetitive;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.utils.Constants;

@Component
public class ReUseOperation {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TransactionBankRepo transactionRepo;

	@Autowired
	ClientBankRepo clientRepo;
	
	
	@Scheduled(fixedRate = 3600000, initialDelay = 3600000)
	@Transactional(rollbackFor = GenericError.class)
	public void repeatReversal() throws GenericError {
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
			List<ClientBank> clients = clientRepo.findByStatus(Constants.NOT_VERIFICATE);
			if(clients.isEmpty()) {
				logger.info("NO CLIENT WITHOUT VETRIFICATION ACCOUNT FOUNDED");
			} else {
				StringBuilder sb = new StringBuilder();
				clients.forEach(x -> sb.append(x.getFirstName()).append(" ").append(x.getLastName()));
				logger.info("CLIENT WITH VERIFICATION ACCOUNT FOUNDED - DELETING THEM IS STARTING: {}", sb.toString());
				clientRepo.deleteAll(clients);
			}
			
		} catch (Exception e) {
			throw new GenericError(new StringBuilder("ERROR IN REVERSAL METHOD. ERROR ").append(e).toString());
		}
		logger.info("REVERSAL CHECK ENDING..."); 
	}
	
	
//	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//	
//	
//	public void startDelayedThread(String panCode) {
//		this.panCode = panCode;
//		scheduler.schedule(this::runThread, 30, TimeUnit.SECONDS);
//	}
//	
//	@Transactional
//	public void runThread() {
//		try {
//			
//			ClientBank clients = clientRepo.findByStatusAndPanCode(Constants.IS_VERIFICATING, panCode); 
//			clients.setStatus(Constants.NOT_VERIFICATE);
//			clientRepo.save(clients);
//			
//		} catch (Exception e) {
//			logger.error("ERROR FOUND NOT VERIFICATE ACCOUNT: {}", e);
//		}
//	}

	
	
}
