package com.progetto.service_impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.domain.VerificationToken;
import com.progetto.dto.ResponseFromApi;
import com.progetto.logic.ExcelReaderLogic;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.repository.VerificationTokenRepo;
import com.progetto.service.IexcelReader;
import com.progetto.utils.Constants;
import com.progetto.validators.ExcelCustomValidator;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExcelReader implements IexcelReader {

	@Autowired
	ClientAccountBankRepo bankRepo;

	@Autowired
	ClientBankRepo clientRepo;

	@Autowired
	TransactionBankRepo transactionRepo;

	@Autowired
	VerificationTokenRepo tokenRepo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ResponseFromApi readExcelData(MultipartFile excel) throws Exception {
		ExcelCustomValidator.validateExcelFile(excel);
		String message;
		HttpStatus status;
		try (OPCPackage opcPackage = OPCPackage.open(excel.getInputStream());
				XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);) {
			ExcelCustomValidator.validWorkbook(workbook);
			List<ClientBank> clientList = new ArrayList<>();
			List<Transaction> transactionList = new ArrayList<>();
			List<BankAccount> bankAccountList = new ArrayList<>();
			List<VerificationToken> verificationTokenList = new ArrayList<>();
			for (int sheetsIndex = 0; sheetsIndex < workbook.getNumberOfSheets(); sheetsIndex++) {
				XSSFSheet sheet = workbook.getSheetAt(sheetsIndex);

				for (int row = 1; row <= sheet.getLastRowNum(); row++) {
					switch (sheet.getSheetName().toLowerCase()) {
					case Constants.CLIENT:
						clientList.add(ExcelReaderLogic.createClientFromExcel(sheet.getRow(row)));
						break;
					case Constants.TRANSACTION:
						transactionList
								.add(ExcelReaderLogic.createTransactionFromExcel(sheet.getRow(row), bankAccountList));
						break;
					case Constants.BANK_ACCOUNT:
						bankAccountList.add(ExcelReaderLogic.createBankAccountFromExcel(sheet.getRow(row), clientList));
						break;
					case Constants.REGISTRATION_TOKEN:
						verificationTokenList
								.add(ExcelReaderLogic.createVerificationTokenFromExcel(sheet.getRow(row), clientList));
					}
				}
			}
			ExcelCustomValidator.validList(clientList, transactionList, bankAccountList, verificationTokenList);
			clientRepo.saveAll(clientList);
			bankRepo.saveAll(bankAccountList);
			transactionRepo.saveAll(transactionList);
			tokenRepo.saveAll(verificationTokenList);
			message = "VALUE INSERT CORRECTLY FROM EXCEL TO DATABASE";
			status = HttpStatus.OK;
		} catch (Exception e) {
			message = new StringBuilder("ERROR READING AND MAPPING EXCEL: ").append(e).toString();
			status = HttpStatus.BAD_REQUEST;
			logger.error(message);
		}
		return new ResponseFromApi(message, status);
	}

}
