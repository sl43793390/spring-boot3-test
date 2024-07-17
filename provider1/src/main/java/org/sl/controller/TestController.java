package org.sl.controller;

import org.sl.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

    @Value("${name}")
    private String name;

    @GetMapping(value = "/name")
    public String getName(){
        System.out.println(name);
        return name;
    }

    @GetMapping(value = "/test/{id}")
    public String test(@PathVariable("id") String p){
        System.out.println(p);
        return "success";
    }
    @GetMapping(value ="/user")
    public User getUserById(@RequestParam("userId") String userId){
        User u = new User();
        u.setUserId(userId);
        u.setEmail("test@123.com");
        u.setUserName("testusername");
        return u;
    }

}
