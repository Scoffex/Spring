package it.auriga.scheduler.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.auriga.scheduler.filter.CsvFilter;
import it.auriga.scheduler.utils.PathManager;
import it.auriga.scheduler.utils.ZipperProperties;

@Service
public class CsvZipper {

	@Autowired
	private ZipperProperties zipperProp;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Scheduled(cron = "0 */1 * ? * *") //0 1 0 1 * ? CRON PER LE 00:01 DEL PRIMO GIORNO DI OGNI MESE
	public void createZipFile() throws IOException {
		
		FileInputStream inputStrem = null;
		FileOutputStream outputStream = null;
		ZipOutputStream zip = null;
		PathManager pathManager = null;
		try {
			logger.info("CSV ZIPPER STARTING AT {}", new Date());
			pathManager = new PathManager(zipperProp);
			File[] filesFounded = new File(zipperProp.getPath_file()).listFiles(new CsvFilter(zipperProp));
			logger.info("NUMBER OF FILE FOUND: {}", filesFounded.length);
			
			if (filesFounded.length > 0) {
				String pathOfFile = pathManager.getPathOfLocationZipper();
				outputStream = new FileOutputStream(pathOfFile);
				zip = new ZipOutputStream(outputStream);
				logger.info("ZIP FILE LOCATION {}", pathOfFile);
				for (File file : filesFounded) {
					zip.putNextEntry(new ZipEntry(file.getName()));
					logger.info("FILE SELECTED: {}", file.getName());
					inputStrem = new FileInputStream(file);
					byte[] buffer = new byte[1024];
					int len;
					while ((len = inputStrem.read(buffer)) > 0) {
						zip.write(buffer, 0, len);
					}
					zip.closeEntry();
				}
			} else {
				String error = new StringBuilder("NO FILE WITH ").append(zipperProp.getFile_name_filter())
						.append(" PRESENT IN THE FOLDER ").append(zipperProp.getPath_file()).toString();
				logger.error("ERROR: {}", error);
			}
		} catch (FileNotFoundException e) {
				pathManager.createFolderIfNotExist();
				createZipFile();
		} catch (Exception e) {
			logger.error("ERROR IN ZIPPER METHOD: ", e);
		} finally {
			if (inputStrem != null) 
				inputStrem.close();
			if (zip != null)
				zip.close();
			if (outputStream != null)
				outputStream.close();
		}

	}

}
