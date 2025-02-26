package com.mph.salelaptop.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long orderItemId;
    private Long orderId;   // Chỉ cần ID của Order
    private Long productId; // Chỉ cần ID của Product

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private Integer quantity;
    private BigDecimal price; // Giá tại thời điểm mua
}
