package com.progetto.logic;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.domain.VerificationToken;
import com.progetto.exception.GenericError;

public class ExcelReaderLogic {

	private static Logger logger = LoggerFactory.getLogger(ExcelReaderLogic.class);
	
	private static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
	
	public static ClientBank createClientFromExcel(XSSFRow row) throws GenericError {
		try {
			logger.info("ROW NUM: {}", row.getRowNum());
			ClientBank client = new ClientBank();
			client.setId(row.getCell(0).getStringCellValue());
			client.setBorningDate(LocalDate.parse(row.getCell(1).getStringCellValue()));
			client.setCreationDate(LocalDate.parse(row.getCell(2).getStringCellValue()));
			client.setEmail(row.getCell(3).getStringCellValue());
			client.setExpirationDate(LocalDate.parse(row.getCell(4).getStringCellValue()));
			client.setFirstName(row.getCell(5).getStringCellValue());
			client.setLastName(row.getCell(6).getStringCellValue());
			client.setPanCode(row.getCell(7).getStringCellValue());
			client.setPassword(row.getCell(8).getStringCellValue());
			client.setStatus(row.getCell(9).getStringCellValue());
			logger.info("CLIENT SAVED: {}", client.toString());
			return client;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING CLIENT VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public static Transaction createTransactionFromExcel(XSSFRow row, List<BankAccount> bankAccountList) throws GenericError {
		try {
			logger.info("ROW NUM: {}", row.getRowNum());
			Transaction transaction = new Transaction();
			transaction.setId(row.getCell(0).getStringCellValue());
			transaction.setAmount(row.getCell(1).getNumericCellValue());
			transaction.setAtmCode(row.getCell(2).getStringCellValue());
			transaction.setOperation(row.getCell(3).getStringCellValue());
			transaction.setStatus(row.getCell(4).getStringCellValue());
			Date date = simpleDate.parse(row.getCell(5).getStringCellValue());
			transaction.setTransactionTime(date);
			for (BankAccount ba : bankAccountList) {
				if (ba.getId().equals(row.getCell(6).getStringCellValue()))
					transaction.setBankAccount(ba);
			}
			logger.info("TRANSACTION SAVED: {}", transaction.toString());
			return transaction;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING TRANSACTION VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public static BankAccount createBankAccountFromExcel(XSSFRow row, List<ClientBank> clientList) throws GenericError {
		try {
			logger.info("ROW NUM: {}", row.getRowNum());
			BankAccount bankAccount = new BankAccount();
			bankAccount.setId(row.getCell(0).getStringCellValue());
			bankAccount.setAmount(row.getCell(1).getNumericCellValue());
			bankAccount.setCurrency(row.getCell(2).getStringCellValue());
			bankAccount.setDateCreationAccount(LocalDate.parse(row.getCell(3).getStringCellValue()));
			bankAccount.setIban(row.getCell(4).getStringCellValue());
			for (ClientBank client : clientList) {
				if (client.getId().equals(row.getCell(5).getStringCellValue()))
					bankAccount.setClientBank(client);
			}
			logger.info("BANK ACCOUNT SAVED: {}", bankAccount.toString());
			return bankAccount;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING BANK ACCOUNT VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public static VerificationToken createVerificationTokenFromExcel(XSSFRow row, List<ClientBank> clientList)
			throws GenericError {
		try {
			logger.info("ROW NUM: {}", row.getRowNum());
			VerificationToken token = new VerificationToken();
			token.setId(row.getCell(0).getStringCellValue());
			Date date = simpleDate.parse(row.getCell(1).getStringCellValue());
			token.setCreationTime(date);
			date = simpleDate.parse(row.getCell(2).getStringCellValue());
			token.setExpirationTime(date);
			token.setToken(row.getCell(3).getStringCellValue());
			for (ClientBank client : clientList) {
				if (client.getId().equals(row.getCell(4).getStringCellValue()))
					token.setClientBank(client);
			}
			logger.info("VERIFICATION TOKEN SAVED: {}", token.toString());
			return token;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING REGISTRATION TOKEN VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}
}
