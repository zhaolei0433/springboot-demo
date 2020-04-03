package com.example.mqdemo.config;

import com.example.mqdemo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaolei
 * Create: 2020/1/7 11:26
 * Modified By:
 * Description:
 */
@Configuration
public class RabbitConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(RabbitConfig.class);

    @Resource
    private CachingConnectionFactory connectionFactory;

    /*******************************************交换器配置*********************************************/
    //目标转换器，需要哪种类型的转换器就创建哪种类型的转换器
    @Bean
    public DirectExchange exchangeHello(){
        Map<String, Object> eArguments = new HashMap<>();
        //备份交换器参数
        eArguments.put("alternate-exchange", "exchange.ae");
        return new DirectExchange("exchange.hello",true,false,eArguments);
    }
    //备份转换器
    @Bean
    public FanoutExchange exchangeAE(){
        return new FanoutExchange("exchange.ae",true,false,null);
    }
    //死信转换器
    @Bean
    public TopicExchange exchangeDLX(){
        return new TopicExchange("exchange.dlx",true,false,null);
    }

    /*******************************************队列配置*********************************************/
    /**
     * 定义一个hello的队列
     * Queue 可以有5个参数
     *      1.队列名
     *      2.durable       持久化消息队列 ,rabbitmq重启的时候不需要创建新的队列 默认true
     *      3.auto-delete   表示消息队列没有在使用时将被自动删除 默认是false
     *      4.exclusive     表示该消息队列是否只在当前connection生效,默认是false
     *      5.arguments     表示该消息队列绑定的死信队列信息
     */
    @Bean
    public Queue queueHello() {
        Map<String, Object> args = new HashMap<>();
        //声明死信交换器
        args.put("x-dead-letter-exchange", "exchange.dlx");
        //声明死信路由键
        args.put("x-dead-letter-routing-key", "dlx.test" );
        //声明队列消息过期时间 5000ms
        args.put("x-message-ttl", 5000);
        return new Queue("queue.hello",true,false,false,args);
    }

    //备份对列
    @Bean
    public Queue queueAE() {
        return new Queue("queue.ae",true,false,false,null);
    }

    //死信对列
    @Bean
    public Queue queueDLX() {
        return new Queue("queue.dlx",true,false,false,null);
    }

    /*******************************************绑定目标队列*********************************************/
    //绑定目标对列
    @Bean
    public Binding bindingExchangeDirect(@Qualifier("queueHello")Queue queueHello, @Qualifier("exchangeHello") DirectExchange exchangeHello){
        return  BindingBuilder.bind(queueHello).to(exchangeHello).with("helloKey");
    }
    //绑定备份对列
    @Bean
    public Binding bindingExchangeAE(@Qualifier("queueAE")Queue queueAE, @Qualifier("exchangeAE") FanoutExchange exchangeAE){
        return  BindingBuilder.bind(queueAE).to(exchangeAE);
    }

    //绑定死信对列
    @Bean
    public Binding bindingExchangeDLX(@Qualifier("queueDLX")Queue queueAE, @Qualifier("exchangeDLX") TopicExchange exchangeDLX){
        return  BindingBuilder.bind(queueAE).to(exchangeDLX).with("dlx.*");
    }

    /**
     * 如果需要在生产者需要消息发送后的回调，
     * 需要对rabbitTemplate设置ConfirmCallback对象，
     * 由于不同的生产者需要对应不同的ConfirmCallback，
     * 如果rabbitTemplate设置为单例bean，
     * 则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory);
    }

   //消息消费者也应配置 MessageConverter 为 Jackson2JsonMessageConverter
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /*@Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
                return null;
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(message.getBody()))){
                    return (Person)ois.readObject();
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
        });

        return factory;
    }*/
}
