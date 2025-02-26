package com.mph.salelaptop.repository;

import com.mph.salelaptop.model.Customer;
import com.mph.salelaptop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
     List<Order> findByCustomer(Customer customer);
     List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}