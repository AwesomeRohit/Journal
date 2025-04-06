package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping()
    public void  createUser( @RequestBody User user){
        userService.saveAll(user);
    }
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser (@RequestBody User user , @PathVariable String username){
        User userIn = userService.findByUserName(username);
        if(userIn !=  null){
            userIn.setUsername(user.getUsername());
            userIn.setPassword(user.getPassword());
            userService.saveAll(userIn);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }
}
