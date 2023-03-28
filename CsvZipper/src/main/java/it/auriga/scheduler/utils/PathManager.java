package it.auriga.scheduler.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Classe di utility per gestire i differenti path e combinare i nomi delle vatie cartelle e file
public class PathManager {

	private ZipperProperties zipperProp;
	
	private  Logger logger = LoggerFactory.getLogger(PathManager.class);
	
	public PathManager(ZipperProperties zipperProp){
		this.zipperProp = zipperProp;
	}

	public String getPathOfLocationZipper() {
		String pathOfFile = "";
		try {
			pathOfFile = new StringBuilder(zipperProp.getPath_zip())
					.append(getFolderName())
					.append(getFileName()).toString();
		} catch (Exception e) {
			logger.error("ERROR IN getPathOfLocationZipper method: ", e);
		}
		return pathOfFile;
	}

	public String getFileName() {
		String fileName = "";
		try {
			fileName = new StringBuilder(zipperProp.getFile_name()).append("_").append(
					new SimpleDateFormat(zipperProp.getSimple_date_zipper()).format(new Date()))
					.append(zipperProp.getExtension_zipper()).toString();
		} catch (Exception e) {
			logger.error("ERROR IN getFileName method: ", e);

		}
		return fileName;
	}
	
	public String getFolderName() {
		String folderName = "";
		try {
			folderName = new StringBuilder("ZIP_OF_").append(
					new SimpleDateFormat(zipperProp.getSimple_date_folder()).format(new Date()))
					.append("\\").toString();
		} catch (Exception e) {
			logger.error("ERROR IN getFolderName method: ", e);
		}
		return folderName;
	}

	public void createFolderIfNotExist() {
		try {
			String folderName = zipperProp.getPath_zip().concat(getFolderName());
			logger.info("CREATING A FOLDER FOR ZIPPER: {}", folderName);
			File file = new File(folderName);
			file.mkdir();
		} catch (Exception e) {
			logger.error("ERROR IN createFolderIfNotEXIST: ", e);
		}
	}
}
