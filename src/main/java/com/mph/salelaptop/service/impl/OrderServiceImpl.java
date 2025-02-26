package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.CustomerDTO;
import com.mph.salelaptop.dto.OrderDTO;
import com.mph.salelaptop.model.Customer;
import com.mph.salelaptop.model.Order;
import com.mph.salelaptop.repository.CustomerRepository;
import com.mph.salelaptop.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.mph.salelaptop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository; // Cần để lấy Customer từ ID

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return order != null ? convertToDto(order) : null;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            // Retrieve Customer from the database based on customerId
            Customer customer = customerRepository.findById(orderDTO.getCustomerId()).orElse(null);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with id " + orderDTO.getCustomerId() + " not found");
            }

            existingOrder.setCustomer(customer);
            existingOrder.setOrderDate(orderDTO.getOrderDate());
            existingOrder.setTotalAmount(orderDTO.getTotalAmount());
            existingOrder.setOrderStatus(orderDTO.getOrderStatus());
            Order updatedOrder = orderRepository.save(existingOrder);
            return convertToDto(updatedOrder);
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        List<Order> orders = orderRepository.findByCustomer(customer);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDto(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        // Manually map orderId because entity Order use orderId instead of id.
        orderDTO.setOrderId(order.getOrderId());

        // Manually map customerId because relationship
        orderDTO.setCustomerId(order.getCustomer().getCustomerId());
        return orderDTO;
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        // Manually map orderId because entity Order use orderId instead of id.
        order.setOrderId(orderDTO.getOrderId());

        // Retrieve the Customer from the database based on customerId
        Customer customer = customerRepository.findById(orderDTO.getCustomerId()).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + orderDTO.getCustomerId() + " not found");
        }
        order.setCustomer(customer);
        return order;
    }
}