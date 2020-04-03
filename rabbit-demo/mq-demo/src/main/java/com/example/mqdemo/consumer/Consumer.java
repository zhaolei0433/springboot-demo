package com.example.mqdemo.consumer;

import com.example.mqdemo.model.Person;
import com.example.mqdemo.utils.GsonUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @RabbitListener(queues = "queue.hello")
    public void consumer(Message message, Channel channel, @Payload Person person) throws IOException {
        LOGGER.info("receive person: " + person);
        LOGGER.info("《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        //这里的true：一次确认所有的交付，false:一次确认交付
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        //这里的true：重新排队，false:丢弃消息
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,true);
    }

    @RabbitListener(queues = "queue.dlx")
    public String consumerDLX(Message message, Channel channel, @Payload Person person) throws IOException {
        LOGGER.info("死信队列 receive person: " + person);
        LOGGER.info("《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        return "success";
    }

    /*@Bean
    public AmqpTemplate amqpTemplate(AmqpTemplate amqpTemplate) {
        return amqpTemplate;
    }

    @Resource
    private AmqpTemplate amqpTemplate;

   @RabbitListener(queues = "queue.hello")
   public void consumer(Message message, Channel channel) throws IOException {
       amqpTemplate.convertAndSend(message.getMessageProperties().getReplyTo(),"success");
       LOGGER.info("receive person: " + GsonUtil.GsonToBean(new String(message.getBody()),Person.class));
       LOGGER.info("《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
       channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
   }

    @RabbitListener(queues = "queue.dlx")
    public void consumerDLX(Message message, Channel channel) throws IOException {
        LOGGER.info("死信队列 receive person: " + GsonUtil.GsonToBean(new String(message.getBody()),Person.class));
        LOGGER.info("《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }*/
}
