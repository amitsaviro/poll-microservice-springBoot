package com.firstProject.controller;

import com.firstProject.userUrl.UserUrlResponse;
import com.firstProject.userUrl.UserUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserUrlController {

    @Autowired
    private UserUrlService userUrlService;

    @GetMapping("/{userId}") //to bring user from the second port
    public UserUrlResponse getUserById(@PathVariable Long userId){
        return userUrlService.getUserUrlById(userId);
    }
}
