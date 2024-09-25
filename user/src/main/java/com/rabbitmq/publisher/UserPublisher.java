package com.rabbitmq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.contract.UserDeleted;
import com.rabbitmq.contract.UserInserted;
import com.rabbitmq.contract.UserUpdated;

@Service
public class UserPublisher  {

	private final RabbitTemplate rabbitTemplate;

    public UserPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }    

	public void publish(UserInserted obj) {
		ObjectMapper objMapper = new ObjectMapper();

		try {
			String jsonString = objMapper.writeValueAsString(obj);
			rabbitTemplate.convertAndSend("user-exchange", jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	}
	
	public void publish(UserUpdated obj) {
		ObjectMapper objMapper = new ObjectMapper();

		try {
			String jsonString = objMapper.writeValueAsString(obj);
			rabbitTemplate.convertAndSend("user-exchange", jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	}
	
	public void publish(UserDeleted obj) {
		ObjectMapper objMapper = new ObjectMapper();

		try {
			String jsonString = objMapper.writeValueAsString(obj);
			rabbitTemplate.convertAndSend("user-exchange", jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	}

}
