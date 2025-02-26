package com.mph.salelaptop.repository;

import com.mph.salelaptop.model.Cart;
import com.mph.salelaptop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomer(Customer customer);
}
