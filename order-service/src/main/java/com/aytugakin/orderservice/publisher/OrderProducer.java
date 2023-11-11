package com.aytugakin.orderservice.publisher;

import com.aytugakin.orderservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${rabbitmq.order.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.order.routingKey}")
    private String routingKey;
    @Value("${rabbitmq.email.routingKey}")
    private String routingEmailKey;

    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    public void sendMessage(OrderEvent orderEvent) {
        LOGGER.info(String.format("Message sent -> %s" , orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchange, routingEmailKey, orderEvent);
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
    }

}
