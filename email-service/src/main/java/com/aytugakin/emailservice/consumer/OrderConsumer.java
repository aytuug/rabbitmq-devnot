package com.aytugakin.emailservice.consumer;

import com.aytugakin.emailservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.email.queue.name}")
    private void consume(OrderEvent event) {
        LOGGER.info(String.format("Email event received => %s" , event.toString()));
    }
}
