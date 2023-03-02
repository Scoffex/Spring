package com.progetto.event;

import org.springframework.context.ApplicationEvent;

import com.progetto.domain.ClientBank;


public class RegistrationCompleteEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientBank client;
	
	private String applicaitonUrl;
	
	public RegistrationCompleteEvent(ClientBank client, String applicaitonUrl) {
		super(client);
		this.client = client;
		this.applicaitonUrl = applicaitonUrl;
	}

	public ClientBank getClient() {
		return client;
	}

	public void setClient(ClientBank client) {
		this.client = client;
	}

	public String getApplicaitonUrl() {
		return applicaitonUrl;
	}

	public void setApplicaitonUrl(String applicaitonUrl) {
		this.applicaitonUrl = applicaitonUrl;
	}

	
}
