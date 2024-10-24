package com.rawat.electrolok.store.services;

import com.rawat.electrolok.store.dtos.PageableResponse;
import com.rawat.electrolok.store.dtos.UserDto;
import com.rawat.electrolok.store.entities.User;

import java.util.List;

public interface UserService {
    // create
    UserDto createUser(UserDto userDto);

    // update
    UserDto updateUser(UserDto userDto, String userId);

    // delete
    void deleteUser(String userId);

    // getAllUser
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    // getSingleUserById
    UserDto getUserById(String userId);

    // get single user Email
    UserDto getUserByEmail(String email);

    // searchUser
    List<UserDto> searchUser(String keyword);

    // other user specific features
}
