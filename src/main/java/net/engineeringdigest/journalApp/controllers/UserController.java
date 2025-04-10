package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAll(){
        return userService.getAll();
    }
   
    @PutMapping()
    public ResponseEntity<?> updateUser (@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();
        User userIn = userService.findByUserName(username);
        userIn.setUsername(user.getUsername());
        userIn.setPassword(user.getPassword());
        userService.saveUser(userIn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();
        User userIn = userService.findByUserName(username);
        userService.deleteById(userIn.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
