package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    CartItemDTO getCartItemById(Long id);
    List<CartItemDTO> getAllCartItems();
    CartItemDTO createCartItem(CartItemDTO cartItemDTO);
    CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO);
    void deleteCartItem(Long id);
}