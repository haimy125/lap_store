package com.mph.salelaptop.dto;

import lombok.Getter;
import lombok.Setter;

public class BrandDTO {
    private Long brandId;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private String brandName;
}
