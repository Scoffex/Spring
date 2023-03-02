package com.progetto.excel;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.utils.Constants;

import jakarta.transaction.Transactional;

@Service
public class ExcelReader implements IexcelReader {

	@Autowired
	ClientAccountBankRepo bankRepo;
	@Autowired
	ClientBankRepo clientRepo;
	@Autowired
	TransactionBankRepo transactionRepo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ResponseFromApi readExcelData(String path) throws Exception {
		String message;
		HttpStatus status;
		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(file);
			for (int sheetsIndex = 0; sheetsIndex < workbook.getNumberOfSheets(); sheetsIndex++) {
				XSSFSheet sheet = workbook.getSheetAt(sheetsIndex);
				for (int row = 1; row <= sheet.getLastRowNum(); row++) {
					mappingValueOnEntity(sheet.getRow(row), sheet.getSheetName());
				}
			}
			message = "VALUE INSERT CORRECTLY FROM EXCEL TO DATABASE";
			status = HttpStatus.OK;
		} catch (Exception e) {
			message = new StringBuilder("ERROR READING AND MAPPING EXCEL: ").append(e).toString();
			status = HttpStatus.BAD_REQUEST;
			logger.error(message);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (file != null) {
				file.close();
			}

		}
		return new ResponseFromApi(message, status);
	}

	@Transactional(rollbackOn = GenericError.class)
	private void mappingValueOnEntity(XSSFRow row, String nameOfSheet) throws ParseException, GenericError {
		try {
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			switch (nameOfSheet.toLowerCase()) {
			case Constants.CLIENT:
				clientRepo.save(createClientFromExcel(row));
				break;
			case Constants.TRANSACTION:
				transactionRepo.save(createTransactionFromExcel(row, simpleDate));
				break;
			case Constants.BANK_ACCOUNT:
				bankRepo.save(createBankAccountFromExcel(row));
				break;
			}
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public ClientBank createClientFromExcel(XSSFRow row) throws GenericError {
		try {
			ClientBank client = new ClientBank();
			client.setId((int) row.getCell(0).getNumericCellValue());
			client.setBorningDate(LocalDate.parse(row.getCell(1).getStringCellValue()));
			client.setCreationDate(LocalDate.parse(row.getCell(2).getStringCellValue()));
			client.setEmail(row.getCell(3).getStringCellValue());
			client.setExpirationDate(LocalDate.parse(row.getCell(4).getStringCellValue()));
			client.setFirstName(row.getCell(5).getStringCellValue());
			client.setLastName(row.getCell(6).getStringCellValue());
			client.setPanCode(row.getCell(7).getStringCellValue());
			client.setPassword(row.getCell(8).getStringCellValue());
			client.setStatus(row.getCell(9).getStringCellValue());
			logger.info(client.toString());
			return client;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING CLIENT VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public Transaction createTransactionFromExcel(XSSFRow row, SimpleDateFormat simpleDate) throws GenericError {
		try {
			Transaction transaction = new Transaction();
			transaction.setId((int) row.getCell(0).getNumericCellValue());
			transaction.setAmount(row.getCell(1).getNumericCellValue());
			transaction.setAtmCode(row.getCell(2).getStringCellValue());
			transaction.setOperation(row.getCell(3).getStringCellValue());
			transaction.setStatus(row.getCell(4).getStringCellValue());
			Date date = simpleDate.parse(row.getCell(5).getStringCellValue());
			transaction.setTransactionTime(date);
			BankAccount bank = bankRepo.findById((int) row.getCell(6).getNumericCellValue())
					.orElseThrow(() -> new GenericError("BANK ACCOUNT NOT FOUND"));
			transaction.setBankAccount(bank);
			logger.info(transaction.toString());
			return transaction;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING TRANSACTION VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

	public BankAccount createBankAccountFromExcel(XSSFRow row) throws GenericError {
		try {
			int cellIndex = 0;
			BankAccount bankAccount = new BankAccount();
			bankAccount.setId((int) row.getCell(0).getNumericCellValue());
			bankAccount.setAmount(row.getCell(1).getNumericCellValue());
			bankAccount.setCurrency(row.getCell(2).getStringCellValue());
			bankAccount.setDateCreationAccount(LocalDate.parse(row.getCell(3).getStringCellValue()));
			bankAccount.setIban(row.getCell(4).getStringCellValue());
			bankAccount.setCurrency(row.getCell(5).getStringCellValue());
			ClientBank clientBank = clientRepo.findById((int) row.getCell(6).getNumericCellValue())
					.orElseThrow(() -> new GenericError("CLIENT NOT FOUND"));
			bankAccount.setClientBank(clientBank);
			return bankAccount;
		} catch (Exception e) {
			String error = new StringBuilder("ERROR IN MAPPING BANK ACCOUNT VALUE: ").append(e).toString();
			logger.error(error);
			throw new GenericError(error);
		}
	}

}
