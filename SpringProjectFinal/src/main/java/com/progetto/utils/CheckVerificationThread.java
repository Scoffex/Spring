//package com.progetto.utils;
//
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.progetto.domain.ClientBank;
//import com.progetto.repository.ClientBankRepo;
//
//@Service
//public class CheckVerificationThread {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	ClientBankRepo repo;
//	
//	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//	public void startDelayedThread() {
//		scheduler.schedule(this::runThread, 10, TimeUnit.MINUTES);
//	}
//
//	@Transactional
//	private void runThread() {
//		try {
//			List<ClientBank> clients = repo.findByStatus(Constants.IS_VERIFICATING);
//			clients.forEach(x -> x.setStatus(Constants.NOT_VERIFICATE));
//		} catch (Exception e) {
//			logger.error("ERROR FOUND NOT VERIFICATE ACCOUNT: {}", e);
//		}
//	}
//}
