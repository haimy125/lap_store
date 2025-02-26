package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.CartItemDTO;
import com.mph.salelaptop.model.Cart;
import com.mph.salelaptop.model.CartItem;
import com.mph.salelaptop.model.Product;
import com.mph.salelaptop.repository.CartItemRepository;
import com.mph.salelaptop.repository.CartRepository;
import com.mph.salelaptop.repository.ProductRepository;
import com.mph.salelaptop.service.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;  // Cần để lấy Cart từ ID

    @Autowired
    private ProductRepository productRepository; // Cần để lấy Product từ ID

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartItemDTO getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        return cartItem != null ? convertToDto(cartItem) : null;
    }

    @Override
    public List<CartItemDTO> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = convertToEntity(cartItemDTO);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return convertToDto(savedCartItem);
    }

    @Override
    public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
        CartItem existingCartItem = cartItemRepository.findById(id).orElse(null);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(cartItemDTO.getQuantity());

            // Lấy Cart từ repository dựa trên ID
            Cart cart = cartRepository.findById(cartItemDTO.getCartId()).orElse(null);
            if (cart == null) {
                throw new IllegalArgumentException("Cart with id " + cartItemDTO.getCartId() + " not found");
            }
            existingCartItem.setCart(cart);

            // Lấy Product từ repository dựa trên ID
            Product product = productRepository.findById(cartItemDTO.getProductId()).orElse(null);
            if (product == null) {
                throw new IllegalArgumentException("Product with id " + cartItemDTO.getProductId() + " not found");
            }
            existingCartItem.setProduct(product);

            CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
            return convertToDto(updatedCartItem);
        }
        return null;
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    private CartItemDTO convertToDto(CartItem cartItem) {
        CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class);

        // Manually map fields that ModelMapper doesn't handle automatically
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setCartId(cartItem.getCart().getCartId());
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());

        return cartItemDTO;
    }

    private CartItem convertToEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);

        // Manually map fields that ModelMapper doesn't handle automatically
        cartItem.setCartItemId(cartItemDTO.getCartItemId());

        // Retrieve Cart and Product from the repository
        Cart cart = cartRepository.findById(cartItemDTO.getCartId()).orElse(null);
        if (cart == null) {
            throw new IllegalArgumentException("Cart with id " + cartItemDTO.getCartId() + " not found");
        }
        cartItem.setCart(cart);

        Product product = productRepository.findById(cartItemDTO.getProductId()).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + cartItemDTO.getProductId() + " not found");
        }
        cartItem.setProduct(product);

        return cartItem;
    }
}