package com.progetto.dto;

import java.time.LocalDate;

public class PanCodeInfo {

	String panCode;
	LocalDate creationDate;
	LocalDate expriationDate;
	
	public String getPanCode() {
		return panCode;
	}
	public void setPanCode(String panCode) {
		this.panCode = panCode;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDate getExpriationDate() {
		return expriationDate;
	}
	public void setExpriationDate(LocalDate expriationDate) {
		this.expriationDate = expriationDate;
	}
	
	
}
