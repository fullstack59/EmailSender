package com.emailsender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.firstproject.user.UserHttpClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	CommandLineRunner runner(RabbitTemplate rabbitTemplate) {
		return args -> {
			
			String EXCHANGE_NAME = "user-exchange";
		    String EXCHANGE_TYPE = "fanout";
			
			ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        factory.setUsername("guest");
	        factory.setPassword("guest");
	        
	        // Establish a connection to RabbitMQ
	        try (Connection connection = factory.newConnection();
	             Channel channel = connection.createChannel()) {

	            // Declare an exchange
	            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true); // true means the exchange is durable
	            channel.queueDeclare(EXCHANGE_NAME, false, false, false, null);
	            
	            System.out.println("Exchange '" + EXCHANGE_NAME + "' created successfully.");
	            
	        }
		};
	}

}
