package com.progetto.dto;

import java.sql.Timestamp;

import com.progetto.utils.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class OperationConfirmDto {

	@NotBlank(message = "ATM CODE CAN'T BE NULL OR EMPTY")
	@Pattern(regexp = Constants.PAN_CODD_REGEX, message = Constants.INVALID_PAN_CODE)
	String atmCode;
	
	@NotNull
	Timestamp operationTime;

	public String getAtmCode() {
		return atmCode;
	}

	public void setAtmCode(String atmCode) {
		this.atmCode = atmCode;
	}

	
	public Timestamp getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

	@Override
	public String toString() {
		return "OperationConfirmDto [atmCode=" + atmCode + ", operationTime=" + operationTime + "]";
	}

	
	
	
	
	
	
}
