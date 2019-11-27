package com.netreaders.controller;

import com.netreaders.dto.BookDto;
import com.netreaders.models.ResponseMessage;
import com.netreaders.models.User;
import com.netreaders.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{username}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseMessage<User> findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }
}
