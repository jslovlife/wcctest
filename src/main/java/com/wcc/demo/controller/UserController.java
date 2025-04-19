package com.wcc.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.wcc.demo.service.UserService;
import com.wcc.demo.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/findByUsername")
    public ResponseEntity<?> findUserByUsername(@RequestParam String username) {

        try{
            User user = userService.findByUsername(username);

            if(user == null){
                log.error("User not found, username: {}", username);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found, username: " + username);
            }

            log.info("User found, user: {}", user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error finding user, username: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error finding user");
        }

    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            
            log.info("User saved, user: {}", savedUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            log.error("Error saving user, user: {}", user, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.update(user);

            log.info("User updated, user: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Error updating user, user: {}", user, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }
}
