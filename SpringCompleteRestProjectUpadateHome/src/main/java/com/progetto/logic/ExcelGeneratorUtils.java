package com.progetto.logic;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.domain.VerificationToken;
import com.progetto.exception.GenericError;

public class ExcelGeneratorUtils {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private CellStyle style;
	private Sheet sheet;
	private XSSFFont font;
	
	public ExcelGeneratorUtils(CellStyle style, XSSFFont font) {
		this.style = style;
		this.font = font;
	}

	
	public void createClientSheet(Sheet sheets, List<ClientBank> listOfClient) throws GenericError {
		try {
			this.sheet = sheets;
			Row row = sheet.createRow(0);
			createCell(row, 0, "Id");
			createCell(row, 1, "Borning Date");
			createCell(row, 2, "Creation pancode date");
			createCell(row, 3, "Email");
			createCell(row, 4, "Expiration pancode date");
			createCell(row, 5, "First name");
			createCell(row, 6, "Last name");
			createCell(row, 7, "Pan code");
			createCell(row, 8, "Password");
			createCell(row, 9, "Status");
			font.setFontHeight(11);
			font.setBold(false);
			style.setFont(font);
			int rowCount = 1;
			for (ClientBank client : listOfClient) {
				row = sheet.createRow(rowCount++);
				int columnCount = 0;
				createCell(row, columnCount++, client.getId());
				createCell(row, columnCount++, client.getBorningDate().toString());
				createCell(row, columnCount++, client.getCreationDate().toString());
				createCell(row, columnCount++, client.getEmail());
				createCell(row, columnCount++, client.getExpirationDate().toString());
				createCell(row, columnCount++, client.getFirstName());
				createCell(row, columnCount++, client.getLastName());
				createCell(row, columnCount++, client.getPanCode());
				createCell(row, columnCount++, client.getPassword());
				createCell(row, columnCount++, client.getStatus());
			}
		} catch (Exception e) {
			String error = new StringBuilder("ERROR CREATING CLIENT SHEET: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}

	}

	public void createTransactionSheet(Sheet sheets, List<Transaction> transactionList) throws GenericError {
		try {
			logger.info("SIAMO DENTRO");
			this.sheet = sheets;
			Row row = sheet.createRow(0);
			createCell(row, 0, "Id");
			createCell(row, 1, "Amount");
			createCell(row, 2, "Atm Code");
			createCell(row, 3, "Operation");
			createCell(row, 4, "Status");
			createCell(row, 5, "Transaction Time");
			createCell(row, 6, "Bank Account Id");
			font.setFontHeight(11);
			font.setBold(false);
			style.setFont(font);
			int rowCount = 1;
			logger.info("LIST OF TRANSACTION " + transactionList.toString());
			for (Transaction transaction : transactionList) {
				row = sheet.createRow(rowCount++);
				int columnCount = 0;
				createCell(row, columnCount++, transaction.getId());
				createCell(row, columnCount++, transaction.getAmount());
				createCell(row, columnCount++, transaction.getAtmCode());
				createCell(row, columnCount++, transaction.getOperation());
				createCell(row, columnCount++, transaction.getStatus());
				createCell(row, columnCount++, transaction.getTransactionTime().toString());
				createCell(row, columnCount++, transaction.getBankAccount().getId());

			}
		} catch (Exception e) {
			String error = new StringBuilder("ERROR CREATING TRANSACTION SHEET: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}

	}

	public void createBankAccountSheet(Sheet sheets, List<BankAccount> bankAccountList) throws GenericError {
		logger.info(bankAccountList.toString());
		try {
			this.sheet = sheets;
			Row row = sheet.createRow(0);
			createCell(row, 0, "Id");
			createCell(row, 1, "Amount");
			createCell(row, 2, "Currency");
			createCell(row, 3, "Date Creation Account");
			createCell(row, 4, "Iban");
			createCell(row, 5, "Client Bank Id");
			font.setFontHeight(11);
			font.setBold(false);
			style.setFont(font);
			int rowCount = 1;
			for (BankAccount bankAccount : bankAccountList) {
				row = sheet.createRow(rowCount++);
				int columnCount = 0;
				createCell(row, columnCount++, bankAccount.getId());
				createCell(row, columnCount++, bankAccount.getAmount());
				createCell(row, columnCount++, bankAccount.getCurrency());
				createCell(row, columnCount++, bankAccount.getDateCreationAccount().toString());
				createCell(row, columnCount++, bankAccount.getIban());
				createCell(row, columnCount++, bankAccount.getClientBank().getId());
			}
		} catch (Exception e) {
			String error = new StringBuilder("ERROR CREATING BANK ACCOUNT SHEET: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public void createRegistrationTokenSheet(Sheet sheets, List<VerificationToken> verificationTokenList) throws GenericError {
		try {
			this.sheet = sheets;
			Row row = sheet.createRow(0);
			createCell(row, 0, "Id");
			createCell(row, 1, "Creation Time");
			createCell(row, 2, "Expiration Time");
			createCell(row, 3, "Token");
			createCell(row, 4, "Client Id");
			font.setFontHeight(11);
			font.setBold(false);
			style.setFont(font);
			int rowCount = 1;
			for (VerificationToken token : verificationTokenList) {
				row = sheet.createRow(rowCount++);
				int columnCount = 0;
				createCell(row, columnCount++, token.getId());
				createCell(row, columnCount++, token.getCreationTime().toString());
				createCell(row, columnCount++, token.getExpirationTime().toString());
				createCell(row, columnCount++, token.getToken());
				createCell(row, columnCount++, token.getClientBank().getId());
			}
		} catch (Exception e) {
			String error = new StringBuilder("ERROR CREATING CLIENT SHEET: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}

	}
	
	private void createCell(Row row, int columnCount, Object valueOfCell) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);

		if (valueOfCell instanceof Integer) {
			cell.setCellValue((Integer) valueOfCell);
		} else if (valueOfCell instanceof Long) {
			cell.setCellValue((Long) valueOfCell);
		} else if (valueOfCell instanceof String) {
			cell.setCellValue((String) valueOfCell);
		} else if (valueOfCell instanceof Double) {
			cell.setCellValue((Double) valueOfCell);
		} else {
			cell.setCellValue((Boolean) valueOfCell);
		}
		cell.setCellStyle(style);
	}

}
