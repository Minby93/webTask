package com.web.web.controllers;

import com.web.web.models.User;
import com.web.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class userController {

    private UserService userService;

    @Autowired
    public userController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user){
        userService.registerUser(user);
    }

    @GetMapping("/find/{id}")
    public User findUserById(@PathVariable("id") int id){
        return userService.findUserById(id);
    }
}