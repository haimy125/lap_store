package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.UserDTO;
import com.mph.salelaptop.model.Role;
import com.mph.salelaptop.model.Users;
import com.mph.salelaptop.repository.RoleRepository;
import com.mph.salelaptop.repository.UserRepository;
import com.mph.salelaptop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getUserById(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        Users user = userOptional.orElse(null); // Sử dụng orElse(null) để lấy Users hoặc null
        return user != null ? modelMapper.map(user, UserDTO.class) : null;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        return user != null ? modelMapper.map(user, UserDTO.class) : null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email);
        return user != null ? modelMapper.map(user, UserDTO.class) : null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Users user = modelMapper.map(userDTO, Users.class);
        Role role = roleRepository.findById(userDTO.getRoleId()).orElse(null); // Lấy Role từ database
        if(role != null){
            user.setRole(role);
        }
        Users savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<Users> existingUserOptional = userRepository.findById(id);
        Users existingUser = existingUserOptional.orElse(null);
        if (existingUser != null) {
            modelMapper.map(userDTO, existingUser);
            Role role = roleRepository.findById(userDTO.getRoleId()).orElse(null); // Lấy Role từ database
            if(role != null){
                existingUser.setRole(role);
            }
            Users updatedUser = userRepository.save(existingUser);
            return modelMapper.map(updatedUser, UserDTO.class);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}