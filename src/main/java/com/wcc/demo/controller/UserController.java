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

import com.wcc.demo.model.enums.ErrorEnum;
import com.wcc.demo.model.response.ErrorResponse;

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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getCode(), ErrorEnum.USER_NOT_FOUND.getMessage()));
            }

            log.info("User found, user: {}", user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error finding user, username: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorEnum.INTERNAL_SERVER_ERROR.getCode(), ErrorEnum.INTERNAL_SERVER_ERROR.getMessage()));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorEnum.INTERNAL_SERVER_ERROR.getCode(), ErrorEnum.INTERNAL_SERVER_ERROR.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if(user.getUsername() == null){
                log.error("Username is required");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorEnum.USERNAME_IS_REQUIRED.getCode(), ErrorEnum.USERNAME_IS_REQUIRED.getMessage()));
            }

            if(user.getPassword() == null){
                log.error("Password is required");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorEnum.PASSWORD_IS_REQUIRED.getCode(), ErrorEnum.PASSWORD_IS_REQUIRED.getMessage()));
            }

            User updatedUser = userService.update(user);

            log.info("User updated, user: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Error updating user, user: {}", user, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorEnum.INTERNAL_SERVER_ERROR.getCode(), ErrorEnum.INTERNAL_SERVER_ERROR.getMessage()));
        }
    }
}
