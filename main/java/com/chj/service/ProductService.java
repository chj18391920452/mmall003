package com.chj.service;

import com.chj.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
public interface ProductService extends IService<Product> {
    public List<Product> findByLevle(Integer level,Integer levelId);
}
