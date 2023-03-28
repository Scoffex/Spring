package it.auriga.scheduler.filter;

import java.io.File;
import java.io.FilenameFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.auriga.scheduler.utils.ZipperProperties;


public class CsvFilter implements FilenameFilter {

	private Logger logger = LoggerFactory.getLogger(CsvFilter.class);
	private ZipperProperties zipperProp;
	
	public CsvFilter(ZipperProperties zipperProp){
		this.zipperProp = zipperProp;
	}
	@Override
	public boolean accept(File dir, String name) {
		boolean accepted = false;
		try {
			accepted = name.startsWith(zipperProp.getFile_name_filter());
		} catch (Exception e) {
			logger.error("ERROR IN CSV FILTER: ", e);
		}
		return accepted;
	}
}