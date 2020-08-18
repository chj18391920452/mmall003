package com.chj.vo;

import lombok.Data;

@Data
public class CartVO {
    private Integer id;
    private Integer productId;
    private String name;
    private String fileName;
    private Float price;
    private Integer quantity;
    private Integer stock;
    private Float cost;
}
