package com.chj.vo;

import com.chj.entity.Product;
import lombok.Data;
import java.util.List;

@Data
public class ProductCategoryVO {
    private Integer id;
    private String name;
    private List<ProductCategoryVO> children;
    private String bannerImg;
    private String topImg;
    private List<Product> products;
}
