//package com.progetto.kafka;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.progetto.dto.ResponseFromApi;
//import com.progetto.utils.Constants;
//
//@Component
//public class KafkaRender {
//
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.GROUP_ID)
//	private void listenGroupFoo(String message) {
//		FileWriter fileWriter = null;
//		try {
//			logger.info(message);
//			fileWriter = new FileWriter(Constants.KAFKA_FILE, true);
//			fileWriter.append(message);
//
//		} catch (Exception e) {
//			logger.error(e.toString());
//		} finally {
//			try {
//				if (fileWriter != null) {
//					fileWriter.close();
//				}
//			} catch (IOException e) {
//				logger.error(e.toString());
//			}
//		}
//
//	}
//	
//	private void sendMessage(String msg, String key) {
//		StringBuilder sb = new StringBuilder("TOPIC: ").append(Constants.TOPIC_NAME)
//				.append(System.getProperty("line.separator")).append(" MESSAGE: ").append(msg)
//				.append(System.getProperty("line.separator")).append(" TIME MESSAGE SENDING: ")
//				.append(new Date().toString()).append(System.getProperty("line.separator")).append(" KEY: ")
//				.append(key);
//		kafkaTemplate.send(Constants.TOPIC_NAME, key, sb.toString());
//	}
//
//	public void printMessage(ResponseFromApi response) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.registerModule(new JavaTimeModule());
//			String json = objectMapper.writeValueAsString(response);
//			sendMessage(json, Constants.IDENTIFICATIVO_KAFKA_BANK);
//		} catch (JsonProcessingException e) {
//			logger.error(e.toString());
//		}
//	}
//}
