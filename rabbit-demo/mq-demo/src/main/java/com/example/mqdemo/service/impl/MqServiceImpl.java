package com.example.mqdemo.service.impl;

import com.example.mqdemo.model.Person;
import com.example.mqdemo.service.IMqService;
import com.example.mqdemo.utils.GsonUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaolei
 * Create: 2020/1/7 10:10
 * Modified By:
 * Description:
 */
@Service
public class MqServiceImpl implements IMqService,RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    private static Logger LOGGER = LoggerFactory.getLogger(MqServiceImpl.class);
    private RabbitTemplate rabbitTemplate;
    private RabbitAdmin rabbitAdmin;

    @Autowired
    public MqServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //如果设置备份队列则不起作用
        rabbitTemplate.setMandatory(true);
        //ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
        rabbitTemplate.setConfirmCallback(this);
        //ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
        rabbitTemplate.setReturnCallback(this);
        //设置消息为json格式传递
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        this.rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
    }

    @Override
    public void send(String string) throws Exception {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        Person person = new Person(1,string,24);

        LOGGER.info("Sender : " + person);
        rabbitTemplate.convertAndSend("exchange.hello","helloKey", person,correlationId);
        /*String response = (String) rabbitTemplate.convertSendAndReceive("exchange.hello","helloKey",person,correlationId);
        LOGGER.info("response : " + response);*/
        //rabbitTemplate.convertAndSend("exchange.hello","helloKey", person,correlationId);
        //convertAndSend(exchange:交换机名称,routingKey:路由关键字,object:发送的消息内容,correlationData:消息ID)
       // Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);

        /*String replyQueueName = channel.queueDeclare().getQueue();
        Message message = MessageBuilder.withBody(GsonUtil.GsonString(person).getBytes())
                .setExpiration(5 * 1000 + "")
                .setReplyTo(replyQueueName)
                .build();
        rabbitTemplate.convertAndSend("exchange.hello","helloKey", message,correlationId);
        boolean received;
        //加一个线程阻塞机制
        CountDownLatch replyLatch = new CountDownLatch(1);

        channel.basicConsume(replyQueueName, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body) throws IOException {
                if ("success".equals(new String(body))) {
                    replyLatch.countDown();
                }
            }
        });
        //等待5秒钟
        received = replyLatch.await(5, TimeUnit.SECONDS);
        LOGGER.info("同步回调:"+received);
        channel.queueDelete(replyQueueName);
        channel.close();*/
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            LOGGER.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        }else{
            LOGGER.info("消息发送失败:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        }
    }

    //消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
    //交换机到队列入队出现错误回调
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        LOGGER.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
    }

    //1、CountDownLatch end = new CountDownLatch(N); //构造对象时候 需要传入参数N
    //　　2、end.await()  能够阻塞线程 直到调用N次end.countDown() 方法才释放线程
    //　　3、end.countDown() 可以在多个线程中调用  计算调用次数是所有线程调用次数的总和
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("子线程"+Thread.currentThread().getName()+"执行完成");
                        latch.countDown();//当前线程调用此方法，则计数减一
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        try {
            System.out.println("主线程"+Thread.currentThread().getName()+"等待子线程执行完成...");
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("主线程"+Thread.currentThread().getName()+"开始执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
