package com.example.mqdemo.controller;

import com.example.mqdemo.service.IMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(@RequestParam("str")String str) throws Exception{
        mqService.send(str);
        return "OK";
    }

}
