package com.rawat.electrolok.store.services.impl;

import com.rawat.electrolok.store.dtos.UserDto;
import com.rawat.electrolok.store.entities.User;
import com.rawat.electrolok.store.repositories.UserRepository;
import com.rawat.electrolok.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

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
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID not Found"));
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
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID not Found"));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("ID not found"));
        UserDto singleUserDto = entityToDto(user);
        return singleUserDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User Email = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not Found"));
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
