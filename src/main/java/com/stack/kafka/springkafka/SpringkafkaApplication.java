package com.stack.kafka.springkafka;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class SpringkafkaApplication {

//	@Value("${spring.kafka.template.default-topic}")
//	private String defaultTopic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringkafkaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				String data = "abcdefgh";
				kafkaTemplate.send("topic1", "key", data);
				System.out.println(">>>>> " + data);
				// kafkaTemplate.send();
			}
		};
	}

	@KafkaListener(id = "myListener", topics = "topic1",
			autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
	public void listen(String data) {
		System.out.println("<<<<< " + data);
	}
}
