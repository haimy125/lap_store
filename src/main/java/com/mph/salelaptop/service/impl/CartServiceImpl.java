package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.CartDTO;
import com.mph.salelaptop.dto.CustomerDTO;
import com.mph.salelaptop.model.Cart;
import com.mph.salelaptop.model.Customer;
import com.mph.salelaptop.repository.CartRepository;
import com.mph.salelaptop.repository.CustomerRepository;
import com.mph.salelaptop.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        return cart != null ? convertToDto(cart) : null;
    }

    @Override
    public CartDTO getCartByCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class); // Convert DTO to entity
        Cart cart = cartRepository.findByCustomer(customer);
        return cart != null ? convertToDto(cart) : null;
    }

    @Override
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = convertToEntity(cartDTO);
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    @Override
    public CartDTO updateCart(Long id, CartDTO cartDTO) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart != null) {
            // Retrieve the Customer from the database based on customerId
            Customer customer = customerRepository.findById(cartDTO.getCustomerId()).orElse(null);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with id " + cartDTO.getCustomerId() + " not found");
            }
            existingCart.setCustomer(customer);
            Cart updatedCart = cartRepository.save(existingCart);
            return convertToDto(updatedCart);
        }
        return null;
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO convertToDto(Cart cart) {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        // Manually map cartId because entity Cart use cartId instead of id.
        cartDTO.setCartId(cart.getCartId());

        // Manually map customerId because relationship
        cartDTO.setCustomerId(cart.getCustomer().getCustomerId());
        return cartDTO;
    }

    private Cart convertToEntity(CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        // Manually map cartId because entity Cart use cartId instead of id.
        cart.setCartId(cartDTO.getCartId());

        // Retrieve the Customer from the database based on customerId
        Customer customer = customerRepository.findById(cartDTO.getCustomerId()).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + cartDTO.getCustomerId() + " not found");
        }
        cart.setCustomer(customer);
        return cart;
    }
}