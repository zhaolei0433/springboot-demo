package com.example.mqdemo2.controller;

import com.example.mqdemo2.service.IMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaolei
 * Create: 2020/1/7 10:11
 * Modified By:
 * Description:
 */
@RestController
public class MqController {

    private IMqService mqService;

    @Autowired
    public MqController(IMqService mqService) {
        this.mqService = mqService;
    }

    @RequestMapping(value = "/createQueue", method = RequestMethod.GET)
    public String createQueue(@RequestParam("queueName")String str) throws Exception{
        mqService.declareAndBindingQueue(str);
        return "OK";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(@RequestParam("str")String str) throws Exception{
        mqService.send(str);
        return "OK";
    }

}
