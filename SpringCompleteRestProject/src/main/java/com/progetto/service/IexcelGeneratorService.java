package com.progetto.service;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.progetto.dto.ResponseFromApi;

public interface IexcelGeneratorService {

	public ResponseFromApi generateExcelFile(XSSFWorkbook workbook) throws IOException;
}
