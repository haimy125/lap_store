package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.CartDTO;
import com.mph.salelaptop.dto.CustomerDTO;

public interface CartService {
    CartDTO getCartById(Long id);
    CartDTO getCartByCustomer(CustomerDTO customerDTO);
    CartDTO createCart(CartDTO cartDTO);
    CartDTO updateCart(Long id, CartDTO cartDTO);
    void deleteCart(Long id);
}