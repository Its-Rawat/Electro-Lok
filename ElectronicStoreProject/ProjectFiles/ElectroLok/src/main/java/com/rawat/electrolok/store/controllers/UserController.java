package com.rawat.electrolok.store.controllers;

import com.rawat.electrolok.store.dtos.ApiResponseMessage;
import com.rawat.electrolok.store.dtos.UserDto;
import com.rawat.electrolok.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // save
        @PostMapping
        public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
            UserDto userDto1 = userService.createUser(userDto);
            return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
        }

    // Update user
        @PutMapping("/{userId}")
        public ResponseEntity<UserDto> updateUser(
                @PathVariable("userId") String userId,
               @Valid @RequestBody UserDto userDto
        ){
            UserDto updatedUserDto = userService.updateUser(userDto, userId);
            return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
        }

    // Delete User by ID
        @DeleteMapping("/{userId}")
        public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
            userService.deleteUser(userId);
            ApiResponseMessage userIsDeletedSuccessfullyMessage = ApiResponseMessage.builder()
                    .message("User is Deleted Successfully")
                    .success(true).httpStatus(HttpStatus.OK)
                    .build();
            return new ResponseEntity<>(userIsDeletedSuccessfullyMessage,HttpStatus.OK);
        }

    // get All User
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
            return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    // get Single User

        @GetMapping("/{userId}")
        public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
            return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
        }

    // get by Email
        @GetMapping("/email/{email}")
        public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
            return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
        }
    // Search User
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }


}
