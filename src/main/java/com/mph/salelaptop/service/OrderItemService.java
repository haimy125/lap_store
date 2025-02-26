package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    OrderItemDTO getOrderItemById(Long id);
    List<OrderItemDTO> getAllOrderItems();
    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);
    void deleteOrderItem(Long id);
}