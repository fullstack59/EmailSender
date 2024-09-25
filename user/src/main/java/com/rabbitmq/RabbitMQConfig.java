package com.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

	@Bean
    public Queue userItemQueue() {
        return new Queue("user-item-queue", true); // Queue name and durability setting
    }

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange("user-exchange");
    }

    @Bean
    public org.springframework.amqp.core.Binding binding(Queue userItemQueue, DirectExchange usergExchange) {
        return BindingBuilder.bind(userItemQueue).to(usergExchange).with("user-item-routing-key");
    }
}
