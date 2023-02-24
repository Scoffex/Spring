package com.progetto.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.ExcelPath;
import com.progetto.dto.ResponseFromApi;
import com.progetto.excel.ExcelGenerator;
import com.progetto.excel.IexcelReader;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.repository.TransactionBankRepo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/spring/admin/excel")
public class ExcelController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ClientBankRepo clientRepo;

	@Autowired
	private TransactionBankRepo transactionRepo;

	@Autowired
	private ClientAccountBankRepo bankRepo;
	
	@Autowired
	IexcelReader excelReader;

	@GetMapping("/export-to-excel")
	public ResponseFromApi exportIntoExcelFile(@RequestBody @Valid ExcelPath path) throws IOException {
		return new ExcelGenerator(clientRepo, transactionRepo, bankRepo).generateExcelFile(path.getExcelPath());
	}
	
	@GetMapping("/export-to-database")
	public ResponseFromApi exportIntoExcelToDatabase(@RequestBody @Valid ExcelPath path) throws Exception {
		return excelReader.readExcelData(path.getExcelPath());
	}
}
