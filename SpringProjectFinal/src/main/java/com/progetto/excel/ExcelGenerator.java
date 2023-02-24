package com.progetto.excel;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.utils.Constants;

@Service
public class ExcelGenerator {

	private Logger logger = LoggerFactory.getLogger(ExcelGenerator.class);
	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet sheet;
	private CellStyle style;
	private XSSFFont font;
	private ClientBankRepo clientRepoClass;
	private TransactionBankRepo transactionBankRepoClass;
	private ClientAccountBankRepo bankRepoClass;

	public ExcelGenerator(ClientBankRepo clientRepo, TransactionBankRepo transactionBankRepo,
		ClientAccountBankRepo bankRepo) {
		workbook = new XSSFWorkbook();
		style = workbook.createCellStyle();
		font = workbook.createFont();
		clientRepoClass = clientRepo;
		transactionBankRepoClass = transactionBankRepo;
		bankRepoClass = bankRepo;
	}

	public ResponseFromApi generateExcelFile(String path) throws IOException {
		FileOutputStream outputStream = null;
		String message;
		HttpStatus status;
		try {
			List<String> listOfTable = new ArrayList<>(
					List.of(Constants.CLIENT, Constants.TRANSACTION, Constants.BANK_ACCOUNT));
			writeTable(listOfTable);
			outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			message = new StringBuilder("EXCEL CORRECTLY CREATED, IS LOCATE AT ").append(path).toString();
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("ERROR GENERATING EXCEL: {}", e);
			message = new StringBuilder("ERROR GENERATING EXCEL: ").append(e).toString();
			status = HttpStatus.BAD_REQUEST;
		} finally {
			workbook.close();
			if (outputStream != null)
				outputStream.close();
		}
		return new ResponseFromApi(message, status);
	}

	private void writeTable(List<String> listOfTable) throws GenericError {
		logger.info("LIST OF TABLE " + listOfTable.toString());
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		ExcelUtils excelUtils = new ExcelUtils(style, font);
		for (String table : listOfTable) {
			switch (table.toLowerCase()) {
			case Constants.CLIENT:
				sheet = workbook.createSheet(Constants.CLIENT.toUpperCase());
				excelUtils.setListOfClient(clientRepoClass.findAll());
				excelUtils.createClientSheet(sheet);
				break;
			case Constants.TRANSACTION:
				sheet = workbook.createSheet(Constants.TRANSACTION.toUpperCase());
				excelUtils.setTransactionList(transactionBankRepoClass.findAll());
				excelUtils.createTransactionSheet(sheet);
				
				break;
			case Constants.BANK_ACCOUNT:
				sheet = workbook.createSheet(Constants.BANK_ACCOUNT.toUpperCase());
				excelUtils.setBankAccountList(bankRepoClass.findAll());
				excelUtils.createBankAccountSheet(sheet);
				break;

			}
		}

	}

}
