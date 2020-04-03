package com.example.mqdemo.service;

import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2020/1/7 10:10
 * Modified By:
 * Description:
 */
public interface IMqService {

    void send(String string) throws IOException, Exception;
}
