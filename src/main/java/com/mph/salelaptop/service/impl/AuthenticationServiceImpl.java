package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.config.UserDetailsImpl;
import com.mph.salelaptop.model.Role;
import com.mph.salelaptop.model.Users;
import com.mph.salelaptop.repository.RoleRepository;
import com.mph.salelaptop.repository.UserRepository;
import com.mph.salelaptop.request.AuthenticationRequest;
import com.mph.salelaptop.request.RegisterRequest;
import com.mph.salelaptop.response.AuthenticationResponse;
import com.mph.salelaptop.service.AuthenticationService;
import com.mph.salelaptop.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + request.getRoleId()));

        Users user = new Users(
                null, // userId sẽ được tạo tự động
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getAddress(),
                true, // enabled
                role
        );
        repository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Users user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(jwtToken);
    }
}