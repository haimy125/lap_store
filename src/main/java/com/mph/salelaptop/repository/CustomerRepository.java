package com.mph.salelaptop.repository;

import com.mph.salelaptop.model.Customer;
import com.mph.salelaptop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUser(Users user);
    Customer findByEmail(String email);
}