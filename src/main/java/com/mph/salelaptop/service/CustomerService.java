package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.CustomerDTO;
import com.mph.salelaptop.dto.UserDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id);
    CustomerDTO getCustomerByUser(UserDTO userDTO);
    CustomerDTO getCustomerByEmail(String email);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomer(Long id);
}