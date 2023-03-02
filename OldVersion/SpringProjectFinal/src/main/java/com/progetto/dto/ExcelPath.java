package com.progetto.dto;

import jakarta.validation.constraints.NotBlank;

public class ExcelPath {

	@NotBlank(message = "Path can't be blank")
	String excelPath;

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}
	
	
}
