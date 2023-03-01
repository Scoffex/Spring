package com.progetto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.progetto.dto.ResponseFromApi;
import com.progetto.service.IexcelGeneratorService;
import com.progetto.service.IexcelReader;
import com.progetto.utils.Constants;

@RestController
@RequestMapping("/spring/admin/excel")
public class ExcelController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IexcelReader excelReader;
	@Autowired
	IexcelGeneratorService excelGenerator;

	@GetMapping("/export-to-excel")
	public ResponseFromApi exportIntoExcelFile() throws IOException {
		return excelGenerator.generateExcelFile(new XSSFWorkbook());
	}

	@PostMapping("/export-to-database")
	public ResponseFromApi exportIntoExcelToDatabase(@RequestParam("excel") MultipartFile file) throws Exception {
		
		return excelReader.readExcelData(file);
	}

	@GetMapping("/download-excel")
	public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
		try {
			InputStreamResource resource = new InputStreamResource(
					new FileInputStream(new File(new StringBuilder(Constants.EXCEL_PATH_SAVE).append(Constants.NAME_OF_EXCEL_FILE).toString())));
			HttpHeaders headers = new HttpHeaders();
			headers.add(Constants.HEADER_EXCEL_KEY,
					new StringBuilder(Constants.HEADER_EXCEL_VALUE).append(Constants.NAME_OF_EXCEL_FILE).toString());
			return ResponseEntity.ok().headers(headers)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
		} catch (FileNotFoundException e) {
			String error = new StringBuilder("ERROR IN DOWNLOAD EXCEL IL FILE DA SCARICARE NON E' DISPOIBILE: ")
					.append(e).toString();
			logger.error(error);
			throw new FileNotFoundException(error);
		}
	}

}
