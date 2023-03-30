package com.progetto.validators;

import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.domain.Transaction;
import com.progetto.domain.VerificationToken;
import com.progetto.utils.Constants;

public class ExcelCustomValidator {

	private ExcelCustomValidator() {
	}

	private static Logger logger = LoggerFactory.getLogger(ExcelCustomValidator.class);

	/**
	 * Verifica se il file Excel passato come parametro ha un formato valido e non è
	 * vuoto.
	 *
	 * @param excel il file Excel da verificare
	 * @throws InvalidFormatException se il formato del file non è valido o se il
	 *                                file è vuoto
	 */
	public static void validateExcelFile(MultipartFile excel) throws InvalidFormatException {
		logger.info("Validazione del file Excel in corso");
		String filename = excel.getOriginalFilename();
		try {
			if (!isValidExcelFormat(excel)) {
				String error = String.format("Il formato del file %s non è valido", filename);
				throw new InvalidFormatException(error);
			}
		} catch (InvalidFormatException e) {
			String error = String.format("Errore durante la validazione del file Excel %s: %s", filename,
					e.getMessage());
			logger.error(error);
			throw new InvalidFormatException(error, e);
		}
	}

	private static boolean isValidExcelFormat(MultipartFile excel) {
		String extension = getFileExtension(excel.getOriginalFilename());
		logger.info(extension);
		return Constants.CONTENT_TYPE_EXCEL.equals(excel.getContentType())
				&& Constants.EXCEL_EXTENSION.equals(extension);
	}

	private static String getFileExtension(String filename) {
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex == -1) {
			return "";
		} else {
			return filename.substring(dotIndex + 1);
		}
	}

	public static void validWorkbook(XSSFWorkbook workbook) throws InvalidFormatException {
		int numberOfSheets = workbook.getNumberOfSheets();
		boolean sheetWithData = false;

		for (int i = 0; i < numberOfSheets; i++) {
			if (workbook.getSheetAt(i).getPhysicalNumberOfRows() > 0)
				sheetWithData = true;
		}

		if (!sheetWithData)
			throw new InvalidFormatException("Il file è vuoto");
	}

	public static void validList(List<ClientBank> clientList, List<Transaction> transactionList,
			List<BankAccount> bankAccountList, List<VerificationToken> verificationTokenList)
			throws InvalidFormatException {
		if (clientList.isEmpty() && transactionList.isEmpty() && bankAccountList.isEmpty()
				&& verificationTokenList.isEmpty())
			throw new InvalidFormatException("ALL SHEET OF YOUR EXCEL ARE EMPTY, NO DATE TO SAVE IN DATABASE");
	}

}