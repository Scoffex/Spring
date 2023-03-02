package com.progetto.domain;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.progetto.utils.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
@Entity
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String token;
	
	private Date expirationTime;
	
	private Date creationTime;
	
	@OneToOne(targetEntity = ClientBank.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cliend_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ClientBank clientBank;

	
	public VerificationToken(ClientBank clientBank, String token) {
		this.token = token;
		this.clientBank = clientBank;
		this.creationTime = new Date();
		this.expirationTime = calculateExpirationDate(Constants.EXPIRATION_TIME);
	}
	
	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationTime = calculateExpirationDate(Constants.EXPIRATION_TIME);
	}
	private Date calculateExpirationDate(int expirationTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.creationTime.getTime());
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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
