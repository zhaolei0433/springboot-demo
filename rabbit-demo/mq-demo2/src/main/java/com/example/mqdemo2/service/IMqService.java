package com.example.mqdemo2.service;

import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2020/1/7 10:10
 * Modified By:
 * Description:
 */
public interface IMqService {

    /**
     * 管理员登录之后声明一个独立队列
     * @param queueName 队列名称
     */
    void declareAndBindingQueue(String queueName);

    /**
     * 发送消息
     * @param string
     */
    void send(String string) throws Exception;
}
