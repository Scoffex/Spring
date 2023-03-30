//
//package com.progetto.kafka;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import com.progetto.utils.Constants;
//
//public class KafkaProducerConfig {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Bean
//	public ProducerFactory<String, String> producerFactory() {
//
//		try {
//			Map<String, Object> configProps = new HashMap<>();
//			configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVER);
//			configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//			configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////			configProps.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 30000);
////			configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000);
////			configProps.put(ProducerConfig.RETRIES_CONFIG, 2);
//			return new DefaultKafkaProducerFactory<>(configProps);
//		} catch (Exception e) {
//			logger.error(new StringBuilder("IMPOSSIBLE CONNECTING TO KAFKA. ERROR: ").append(e).toString());
//		}
//		return null;
//	}
//
//	@Bean
//	public KafkaTemplate<String, String> kafkaTemplate() {
//		try {
//			return new KafkaTemplate<>(producerFactory());
//		} catch (Exception e) {
//			logger.error(e.toString());
//		}
//		return null;
//	}
//}
