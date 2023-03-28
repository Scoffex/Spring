package it.auriga.scheduler.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zipper")
public class ZipperProperties {
	
    private String path_file;
    private String path_zip;
    private String file_name;
    private String extension_zipper;
    private String simple_date_zipper;
    private String simple_date_folder;
    private String file_name_filter;
    
	public String getPath_file() {
		return path_file;
	}
	public void setPath_file(String path_file) {
		this.path_file = path_file;
	}
	public String getPath_zip() {
		return path_zip;
	}
	public void setPath_zip(String path_zip) {
		this.path_zip = path_zip;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getExtension_zipper() {
		return extension_zipper;
	}
	public void setExtension_zipper(String extension_zipper) {
		this.extension_zipper = extension_zipper;
	}
	public String getSimple_date_zipper() {
		return simple_date_zipper;
	}
	public void setSimple_date_zipper(String simple_date_zipper) {
		this.simple_date_zipper = simple_date_zipper;
	}
	public String getSimple_date_folder() {
		return simple_date_folder;
	}
	public void setSimple_date_folder(String simple_date_folder) {
		this.simple_date_folder = simple_date_folder;
	}
	public String getFile_name_filter() {
		return file_name_filter;
	}
	public void setFile_name_filter(String file_name_filter) {
		this.file_name_filter = file_name_filter;
	}
    
	
}


