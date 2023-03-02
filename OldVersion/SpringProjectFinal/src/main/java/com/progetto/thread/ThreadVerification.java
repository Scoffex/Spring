package com.progetto.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progetto.domain.ClientBank;
import com.progetto.repository.ClientBankRepo;
import com.progetto.utils.Constants;

public class ThreadVerification extends Thread {

	private ClientBankRepo clientRepo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String panCode;

	public ThreadVerification(String panCode, ClientBankRepo cb) {
		super();
		this.panCode = panCode;
		this.clientRepo = cb;
	}

	public void run() {

		try {
			Thread.sleep(6000);
			logger.info("THREAD START...");
			ClientBank clients = clientRepo.findByStatusAndPanCode(Constants.IS_VERIFICATING, this.panCode);
			clients.setStatus(Constants.NOT_VERIFICATE);
			clientRepo.save(clients);
			logger.info("THREAD END...");
		} catch (Exception e) {
			logger.error("ERROR IN RUN THREAD. ERROR: {}", e);
		}
	}
}
