package com.rawat.electrolok.store.controllers;

import com.rawat.electrolok.store.dtos.ApiResponseMessage;
import com.rawat.electrolok.store.dtos.ImageResponse;
import com.rawat.electrolok.store.dtos.PageableResponse;
import com.rawat.electrolok.store.dtos.UserDto;
import com.rawat.electrolok.store.services.FileService;
import com.rawat.electrolok.store.services.UserService;
import com.rawat.electrolok.store.services.impl.FileServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

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
        public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) throws IOException {
            userService.deleteUser(userId);
            ApiResponseMessage userIsDeletedSuccessfullyMessage = ApiResponseMessage.builder()
                    .message("User is Deleted Successfully")
                    .success(true).httpStatus(HttpStatus.OK)
                    .build();
            return new ResponseEntity<>(userIsDeletedSuccessfullyMessage,HttpStatus.OK);
        }

    // get All User
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
            ){
            return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize, sortBy,sortDir),HttpStatus.OK);
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

    // Upload User Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image, @PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
//        logger.info("User Image Uploaded {}", userDto.toString());

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).httpStatus(HttpStatus.CREATED).message("This is a Image").build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    // Serve User Image

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("User Image Name: {}",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
    }


}
