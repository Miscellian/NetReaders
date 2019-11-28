package com.netreaders.controller;


import com.netreaders.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    NotificationService service;

    @GetMapping("/notification")
    public void doNotify()  {

    }
}
