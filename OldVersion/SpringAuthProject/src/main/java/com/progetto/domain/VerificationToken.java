package com.progetto.domain;

import java.util.Calendar;
import java.util.Date;

import org.apache.kafka.common.Uuid;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.progetto.utils.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class VerificationToken {

	@Id
	private String id;

	private String token;

	private Date expirationTime;

	private Date creationTime;

	@OneToOne(targetEntity = ClientBank.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "cliend_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ClientBank clientBank;

	public VerificationToken(ClientBank clientBank, String token) {
		this.token = token;
		this.clientBank = clientBank;
		this.creationTime = new Date();
		this.expirationTime = calculateExpirationDate(Constants.EXPIRATION_TIME);
		this.id = new StringBuilder(Uuid.randomUuid().toString()).append(String.valueOf(new Date().getTime())).toString();
	}

	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationTime = calculateExpirationDate(Constants.EXPIRATION_TIME);
	}

	public VerificationToken() {

	}

	private Date calculateExpirationDate(int expirationTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.creationTime.getTime());
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public ClientBank getClientBank() {
		return clientBank;
	}

	public void setClientBank(ClientBank clientBank) {
		this.clientBank = clientBank;
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + id + ", token=" + token + ", expirationTime=" + expirationTime
				+ ", creationTime=" + creationTime + ", clientBank=" + clientBank + "]";
	}

	
}
