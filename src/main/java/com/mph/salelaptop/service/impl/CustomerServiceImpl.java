package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.CustomerDTO;
import com.mph.salelaptop.dto.UserDTO;
import com.mph.salelaptop.model.Customer;
import com.mph.salelaptop.model.Users;
import com.mph.salelaptop.repository.CustomerRepository;
import com.mph.salelaptop.repository.UserRepository;
import com.mph.salelaptop.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return customer != null ? convertToDto(customer) : null;
    }

    @Override
    public CustomerDTO getCustomerByUser(UserDTO userDTO) {
        Users user = modelMapper.map(userDTO, Users.class);
        Customer customer = customerRepository.findByUser(user);
        return customer != null ? convertToDto(customer) : null;
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return customer != null ? convertToDto(customer) : null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setFirstName(customerDTO.getFirstName());
            existingCustomer.setLastName(customerDTO.getLastName());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
            existingCustomer.setAddress(customerDTO.getAddress());

            // Get Users from UserDTO
            Users user = usersRepository.findById(customerDTO.getUserId()).orElse(null);
            if(user == null){
                throw new IllegalArgumentException("User with id " + customerDTO.getUserId() + " not found");
            }
            existingCustomer.setUser(user);
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return convertToDto(updatedCustomer);
        }
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        // Manually map customerId because entity Category use customerId instead of id.
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setUserId(customer.getUser().getUserId());
        return customerDTO;
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        // Manually map customerId because entity Category use customerId instead of id.
        customer.setCustomerId(customerDTO.getCustomerId());

        // Get Users from UserDTO
        Users user = usersRepository.findById(customerDTO.getUserId()).orElse(null);
        if(user == null){
            throw new IllegalArgumentException("User with id " + customerDTO.getUserId() + " not found");
        }
        customer.setUser(user);
        return customer;
    }
}