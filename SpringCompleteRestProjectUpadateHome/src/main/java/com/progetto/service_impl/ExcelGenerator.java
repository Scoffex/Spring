package com.progetto.service_impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.logic.ExcelGeneratorUtils;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.repository.VerificationTokenRepo;
import com.progetto.service.IexcelGeneratorService;
import com.progetto.utils.Constants;

@Service
public class ExcelGenerator implements IexcelGeneratorService{

	@Autowired
	ClientBankRepo clientRepoClass;
	@Autowired
	TransactionBankRepo transactionBankRepoClass;
	@Autowired
	ClientAccountBankRepo bankRepoClass;
	@Autowired
	VerificationTokenRepo tokenRepo;
	
	private Logger logger = LoggerFactory.getLogger(ExcelGenerator.class);
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private CellStyle style;
	private XSSFFont font;

	
	public ResponseFromApi generateExcelFile(XSSFWorkbook workbook) throws IOException {
		this.workbook = workbook;
		this.style = workbook.createCellStyle();
		this.font = workbook.createFont();
		FileOutputStream outputStream = null;
		String message;
		HttpStatus status;
		try {
			List<String> listOfTable = new ArrayList<>(
					List.of(Constants.CLIENT, Constants.BANK_ACCOUNT, Constants.TRANSACTION, Constants.REGISTRATION_TOKEN));
			writeTable(listOfTable);
			outputStream = new FileOutputStream(new StringBuilder(Constants.EXCEL_PATH_SAVE).append(Constants.NAME_OF_EXCEL_FILE).toString());
			workbook.write(outputStream);
			message = new StringBuilder("EXCEL CORRECTLY CREATED, TO DOWNLOAD THE FILE CLICK HERE: ").append(Constants.DOWNLOAD_EXCEL_FILE).toString();
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("ERROR GENERATING EXCEL: ", e);
			message = new StringBuilder("ERROR GENERATING EXCEL:  ").append(e).toString();
			status = HttpStatus.BAD_REQUEST;
		} finally {
			workbook.close();
			if (outputStream != null)
				outputStream.close();
		}
		return new ResponseFromApi(message, status);
	}

	private void writeTable(List<String> listOfTable) throws GenericError {
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		ExcelGeneratorUtils excelUtils = new ExcelGeneratorUtils(style, font);
		for (String table : listOfTable) {
			switch (table.toLowerCase()) {
			case Constants.REGISTRATION_TOKEN:
				sheet = workbook.createSheet(Constants.REGISTRATION_TOKEN.toUpperCase());
				excelUtils.createRegistrationTokenSheet(sheet, tokenRepo.findAll());
				break;
			case Constants.CLIENT:
				sheet = workbook.createSheet(Constants.CLIENT.toUpperCase());
				excelUtils.createClientSheet(sheet, clientRepoClass.findAll());
				break;
			case Constants.TRANSACTION:
				sheet = workbook.createSheet(Constants.TRANSACTION.toUpperCase());
				excelUtils.createTransactionSheet(sheet, transactionBankRepoClass.findAll());
				break;
			case Constants.BANK_ACCOUNT:
				sheet = workbook.createSheet(Constants.BANK_ACCOUNT.toUpperCase());
				excelUtils.createBankAccountSheet(sheet, bankRepoClass.findAll());
				break;

			}
		}

	}

}
