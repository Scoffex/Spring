package com.progetto.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamReader {

	private static Logger logger = LoggerFactory.getLogger(ParamReader.class);

	public static String getParam(String value) {
		FileInputStream file = null;
		Properties prop = new Properties();
		String properties = "";
		try {
			file = new FileInputStream(Constants.URLPROPERTIES);
			prop.load(file);
			properties = prop.getProperty(value);
		} catch (Exception e) {
			logger.error("ERROR IN PARAM READER", e);
		} finally {
			try {
				file.close();
			} catch (Exception e) {
				logger.error("ERROR CLOSING FILE INPUT STREAM", e);
			}
		}

		return properties;
	}
}
