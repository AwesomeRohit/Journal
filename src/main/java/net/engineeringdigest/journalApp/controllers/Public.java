package net.engineeringdigest.journalApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.UserService;
@RestController

@RequestMapping("/api/public")
public class Public {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void  createUser( @RequestBody User user){
        userService.saveNewEntry(user);
    }

}
