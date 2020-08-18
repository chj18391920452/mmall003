package com.chj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chj.entity.Product;
import com.chj.mapper.ProductMapper;
import com.chj.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findByLevle(Integer level, Integer levelId) {
        QueryWrapper wrapper = new QueryWrapper();
        switch (level){
            case 1:
                wrapper.eq("categorylevelone_id", levelId);
                break;
            case 2:
                wrapper.eq("categoryleveltwo_id", levelId);
                break;
            case 3:
                wrapper.eq("categorylevelthree_id", levelId);
                break;
        }
        return this.productMapper.selectList(wrapper);
    }
}
