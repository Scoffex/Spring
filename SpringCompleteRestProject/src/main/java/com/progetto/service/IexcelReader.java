package com.progetto.service;

import org.springframework.web.multipart.MultipartFile;

import com.progetto.dto.ResponseFromApi;

public interface IexcelReader {

	public ResponseFromApi readExcelData(MultipartFile file) throws Exception;
}
