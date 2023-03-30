package com.progetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.progetto.validators"})
@EnableScheduling
public class DemoApplication {

//	@Autowired
//	EmailSenderServiceImpl send;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void tirggerMail() {
//		send.sendSimpleEmail("andrealaera0@gmail.com", "aaaaa", "bbbbbb");
//	}
}
