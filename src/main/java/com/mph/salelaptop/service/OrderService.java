package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.CustomerDTO;
import com.mph.salelaptop.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrderById(Long id);
    List<OrderDTO> getAllOrders();
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    List<OrderDTO> getOrdersByCustomer(CustomerDTO customerDTO);
}