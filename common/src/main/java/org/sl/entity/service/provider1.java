package org.sl.entity.service;

import org.sl.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring3test")
public interface provider1 {

    @GetMapping(value = "name")
    public String getName();

    @GetMapping(value ="/user")
    public User getUserById(@RequestParam("userId") String userId);
}
