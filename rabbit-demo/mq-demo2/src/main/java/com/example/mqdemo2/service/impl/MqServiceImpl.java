package com.example.mqdemo2.service.impl;

import com.example.mqdemo2.model.Person;
import com.example.mqdemo2.service.IMqService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaolei
 * Create: 2020/1/7 10:10
 * Modified By:
 * Description:
 */
@Service
public class MqServiceImpl implements IMqService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private static Logger LOGGER = LoggerFactory.getLogger(MqServiceImpl.class);
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchangeHello;
    private FanoutExchange exchangeAE;
    private TopicExchange exchangeDLX;
    private RabbitAdmin rabbitAdmin;
    private Map<String, Object> arguments = new HashMap<>();

    @Autowired
    public MqServiceImpl(RabbitTemplate rabbitTemplate, DirectExchange exchangeHello, FanoutExchange exchangeAE, TopicExchange exchangeDLX) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeHello = exchangeHello;
        this.exchangeAE = exchangeAE;
        this.exchangeDLX = exchangeDLX;
        this.rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        //如果设置备份队列则不起作用
        rabbitTemplate.setMandatory(true);
        //ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
        rabbitTemplate.setConfirmCallback(this);
        //ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
        rabbitTemplate.setReturnCallback(this);
        //设置消息为json格式传递
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void declareAndBindingQueue(String queueName) {
        //创建备份队列
        Queue queueAE = new Queue("queue." + queueName + ".ae", true, false, false, null);
        //创建死信队列
        //Queue queueDLX = new Queue("queue." + queueName + ".dlx", true, false, false, null);
        //声明队列消息过期时间 5000ms
        arguments.put("x-message-ttl", 5000);
        // 队列超过多久没访问失效 30分钟无访问队列失效
        arguments.put("x-expires", 60 * 30 * 1000);
        //声明死信交换器
        //arguments.put("x-dead-letter-exchange", "exchange.dlx");
        //声明死信路由键
        //arguments.put("x-dead-letter-routing-key", "dlx.test");
        //创建队列
        Queue queue = new Queue("queue." + queueName, true, false, false, arguments);
        //申明备份队列
        rabbitAdmin.declareQueue(queueAE);
        //rabbitAdmin.declareQueue(queueDLX);
        rabbitAdmin.declareQueue(queue);
        //绑定队列
        rabbitAdmin.declareBinding(BindingBuilder.bind(queueAE).to(exchangeAE));
        //rabbitAdmin.declareBinding(BindingBuilder.bind(queueDLX).to(exchangeDLX).with("dlx.*"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchangeHello).with("helloKey"));
    }

   /* @Override
    public void send(String string) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        Person person = new Person(1, string, 24);
        LOGGER.info("Sender : " + person);
        //convertAndSend(exchange:交换机名称,routingKey:路由关键字,object:发送的消息内容,correlationData:消息ID)
        rabbitTemplate.convertAndSend("exchange.hello", "helloKey", person, correlationId);
    }*/
   Long waitTimes = 10L;
   public void send(String string) throws Exception {
       Person person = new Person(1, string, 24);
       LOGGER.info("Sender : " + person);
       Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
       String replyQueueName = channel.queueDeclare().getQueue();
       Message message = MessageBuilder.withBody(SerializationUtils.serialize(person))
               .setExpiration(waitTimes * 1000 + "")
               .setReplyTo(replyQueueName)
               .build();
       CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
       rabbitTemplate.send("exchange.hello","helloKey", message,correlationId);
       /*boolean received;
       CountDownLatch replyLatch = new CountDownLatch(1);
       channel.basicConsume(replyQueueName, new DefaultConsumer(channel) {
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope,
                                      AMQP.BasicProperties properties, byte[] body) throws IOException {
               if ("success".equals(new String(body))) {
                   replyLatch.countDown();
               }
           }
       });
       received = replyLatch.await(waitTimes, TimeUnit.SECONDS);
       channel.queueDelete(replyQueueName);
       channel.close();
       return received;*/

   }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            LOGGER.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        } else {
            LOGGER.info("消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        }
    }

    //消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        LOGGER.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
    }
}
