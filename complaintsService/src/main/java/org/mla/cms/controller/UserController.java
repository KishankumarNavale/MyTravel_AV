package org.mla.cms.controller;


import lombok.AllArgsConstructor;
import org.mla.cms.model.Users;
import org.mla.cms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    // build create Users REST API
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        Users savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Integer userId){
        Users user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Build Update Users REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/users/1
    public ResponseEntity<Users> updateUser(@PathVariable("id") Integer userId,
                                           @RequestBody Users user){
        user.setUserId(userId);
        Users updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    // Build Delete Users REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("Users successfully deleted!", HttpStatus.OK);
    }
}