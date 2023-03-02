package com.progetto.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.aspectj.weaver.ast.Instanceof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.exception.GenericError;

public class ExcelUtils {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private CellStyle style;
	private Sheet sheet;
	private XSSFFont font;
	private List<ClientBank> listOfClientClass;
	private List<Transaction> transactionListClass;
	private List<BankAccount> bankAccountListClass;

	public ExcelUtils(CellStyle style, XSSFFont font) {
		this.style = style;
		this.font = font;
	}

	public void setListOfClient(List<ClientBank> listOfClient) {
		this.listOfClientClass = listOfClient;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionListClass = transactionList;
	}

	public void setBankAccountList(List<BankAccount> bankAccountList) {
		this.bankAccountListClass = bankAccountList;
	}

	public void createClientSheet(Sheet sheets) throws GenericError {
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
			for (ClientBank client : listOfClientClass) {
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

	public void createTransactionSheet(Sheet sheets) throws GenericError {
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
			logger.info("LIST OF TRANSACTION " + transactionListClass.toString());
			for (Transaction transaction : transactionListClass) {
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

	public void createBankAccountSheet(Sheet sheets) throws GenericError {
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
			for (BankAccount bankAccount : bankAccountListClass) {
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
