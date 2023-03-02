package com.progetto.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.progetto.utils.Constants;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		try {
			Map<String, Object> props = new HashMap<>();
			props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVER);
			props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.GROUP_ID);
			props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 30000);
			
			
			return new DefaultKafkaConsumerFactory<>(props);
		} catch (Exception e) {
			logger.error(new StringBuilder("IMPOSSIBLE CONNECTING TO KAFKA. ERROR: ").append(e).toString());
		}
		return null;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
