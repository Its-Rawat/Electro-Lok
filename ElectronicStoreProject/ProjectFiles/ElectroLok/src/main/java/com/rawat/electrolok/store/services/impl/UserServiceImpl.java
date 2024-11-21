package com.rawat.electrolok.store.services.impl;

import com.rawat.electrolok.store.Helper.helper;
import com.rawat.electrolok.store.dtos.PageableResponse;
import com.rawat.electrolok.store.dtos.UserDto;
import com.rawat.electrolok.store.entities.User;
import com.rawat.electrolok.store.exceptions.ResourceNotFoundException;
import com.rawat.electrolok.store.repositories.UserRepository;
import com.rawat.electrolok.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath ;

    @Override
    public UserDto createUser(UserDto userDto) {

        // Generate UserId in String format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        // dto -> Entity
        User user  = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
       // Entity -> dto
        UserDto newDto = entityToDto(savedUser);

        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ID not Found"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        // save
        User updatedUser = userRepository.save(user);
        UserDto updatedUserDto = entityToDto(updatedUser);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ID not Found"));
        userRepository.delete(user);

        // delete user profile image
        String fullPath = imagePath + user.getImageName();

        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(NoSuchFileException e){
            logger.info("User Image not found at {}",fullPath);
        }

    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        // pageNumber default starts from 0
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> pageableResponse = helper.getPageableResponse(page, UserDto.class);
        return pageableResponse;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("ID not found"));
        UserDto singleUserDto = entityToDto(user);
        return singleUserDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User Email = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not Found"));
       return entityToDto(Email);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }



// Conversion


    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .Gender(userDto.getGender())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName())
//                .build();

        return mapper.map(userDto, User.class);
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .Gender(savedUser.getGender())
//                .password(savedUser.getPassword())
//                .email(savedUser.getEmail())
//                .imageName(savedUser.getImageName())
//                .about(savedUser.getAbout())
//                .build();
        return mapper.map(savedUser,UserDto.class);
    }


}
