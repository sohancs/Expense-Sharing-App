package org.wt.com.expense_sharing_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wt.com.expense_sharing_app.DTO.APIResponseDTO;
import org.wt.com.expense_sharing_app.DTO.UserDTO;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data("User has been created with username : " + userService.createUser(userDTO).getName())
            .build());
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data(userService.getAllUsers())
            .build());
    }
}

