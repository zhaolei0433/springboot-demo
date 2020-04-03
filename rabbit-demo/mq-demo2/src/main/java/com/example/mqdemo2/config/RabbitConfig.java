package com.example.mqdemo2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
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
}
