package com.netreaders.controller;


import com.netreaders.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping("/notification")
    public void doNotify() {

    }
}
