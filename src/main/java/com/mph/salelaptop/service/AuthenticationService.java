package com.mph.salelaptop.service;

import com.mph.salelaptop.request.AuthenticationRequest;
import com.mph.salelaptop.request.RegisterRequest;
import com.mph.salelaptop.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}