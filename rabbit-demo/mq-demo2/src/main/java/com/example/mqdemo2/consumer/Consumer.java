package com.example.mqdemo2.consumer;

import com.example.mqdemo2.model.Person;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2020/1/7 13:47
 * Modified By:
 * Description:
 */
@Component
public class Consumer {
    private static Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    /*@RabbitListener(queues = "queue.hello")
    public void consumer(Message message, Channel channel, @Payload Person person) throws IOException {
        LOGGER.info("receive person: " + person);
        LOGGER.info("《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    @RabbitListener(queues = "queue.hello.dlx")
    public void consumerDLX(Message message, Channel channel, @Payload Person person) throws IOException {
        LOGGER.info("receive person: " + person);
        LOGGER.info("《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }*/
}
