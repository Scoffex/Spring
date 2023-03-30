//package com.progetto.kafka;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//import com.progetto.utils.Constants;
//
//@Configuration
//public class KafkaTopicConfig {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Bean
//	public KafkaAdmin kafkaAdmin() {
//		try {
//			Map<String, Object> configs = new HashMap<>();
//			configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVER);
//			return new KafkaAdmin(configs);
//		} catch (Exception e) {
//			logger.error(e.toString()); 
//			return null;
//		}
//	}
//
//	@Bean
//	public NewTopic topic1() {
//		return new NewTopic("baeldung", 1, (short) 1);
//	}
//	
//	
//	
//}
