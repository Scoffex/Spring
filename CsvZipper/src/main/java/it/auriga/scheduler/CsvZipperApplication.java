package it.auriga.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CsvZipperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvZipperApplication.class, args);
	}

}
