package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@Controller
public class SpringbootJspApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJspApplication.class, args);
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login_failure")
    public String loginFailure(ModelMap modelMap){
        modelMap.put("login_error", "用户名或密码错误");
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
