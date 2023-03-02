package com.progetto.excel;

import com.progetto.dto.ResponseFromApi;

public interface IexcelReader {

	public ResponseFromApi readExcelData(String path) throws Exception;
}
