package org.sl.controller;

import cn.hutool.json.JSON;
import org.sl.entity.User;
import org.sl.entity.service.provider1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConsumerController {

    @Autowired
    private provider1 provider12;

    @GetMapping(value = "name")
    public String getName(){
        String name = provider12.getName();
        User test123 = provider12.getUserById("test123");
        System.out.println(test123);
        System.out.println(name);
        return name;
    }



}
