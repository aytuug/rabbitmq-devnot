package com.aytugakin.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.order.queue.name}")
    private String queue;
    @Value("${rabbitmq.email.queue.name}")
    private String emailQueue;
    @Value("${rabbitmq.order.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.order.routingKey}")
    private String routingKey;
    @Value("${rabbitmq.email.routingKey}")
    private String routingEmailKey;

    @Bean
    public Queue orderQueue() {
        return new Queue(queue);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding bindingEmail() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(routingEmailKey);
    }

    @Bean
    public MessageConverter convert() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(convert());
        return rabbitTemplate;
    }
}
