package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.OrderItemDTO;
import com.mph.salelaptop.model.Order;
import com.mph.salelaptop.model.OrderItem;
import com.mph.salelaptop.model.Product;
import com.mph.salelaptop.repository.OrderItemRepository;
import com.mph.salelaptop.repository.OrderRepository;
import com.mph.salelaptop.repository.ProductRepository;
import com.mph.salelaptop.service.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository; // Cần để lấy Order từ ID

    @Autowired
    private ProductRepository productRepository; // Cần để lấy Product từ ID

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        return orderItem != null ? convertToDto(orderItem) : null;
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = convertToEntity(orderItemDTO);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return convertToDto(savedOrderItem);
    }

    @Override
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem existingOrderItem = orderItemRepository.findById(id).orElse(null);
        if (existingOrderItem != null) {

            // Retrieve Order from the database based on orderId
            Order order = orderRepository.findById(orderItemDTO.getOrderId()).orElse(null);
            if (order == null) {
                throw new IllegalArgumentException("Order with id " + orderItemDTO.getOrderId() + " not found");
            }
            existingOrderItem.setOrder(order);

            // Retrieve Product from the database based on productId
            Product product = productRepository.findById(orderItemDTO.getProductId()).orElse(null);
            if (product == null) {
                throw new IllegalArgumentException("Product with id " + orderItemDTO.getProductId() + " not found");
            }
            existingOrderItem.setProduct(product);

            existingOrderItem.setQuantity(orderItemDTO.getQuantity());
            existingOrderItem.setPrice(orderItemDTO.getPrice());

            OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
            return convertToDto(updatedOrderItem);
        }
        return null;
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    private OrderItemDTO convertToDto(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);

        // Manually map orderItemId because entity OrderItem use orderItemId instead of id.
        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());

        // Manually map orderId and productId because relationship
        orderItemDTO.setOrderId(orderItem.getOrder().getOrderId());
        orderItemDTO.setProductId(orderItem.getProduct().getProductId());

        return orderItemDTO;
    }

    private OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);

        // Manually map orderItemId because entity OrderItem use orderItemId instead of id.
        orderItem.setOrderItemId(orderItemDTO.getOrderItemId());

        // Retrieve Order and Product from the database based on orderId and productId
        Order order = orderRepository.findById(orderItemDTO.getOrderId()).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("Order with id " + orderItemDTO.getOrderId() + " not found");
        }
        orderItem.setOrder(order);

        Product product = productRepository.findById(orderItemDTO.getProductId()).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + orderItemDTO.getProductId() + " not found");
        }
        orderItem.setProduct(product);
        return orderItem;
    }
}