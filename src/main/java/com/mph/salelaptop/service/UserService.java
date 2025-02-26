package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}