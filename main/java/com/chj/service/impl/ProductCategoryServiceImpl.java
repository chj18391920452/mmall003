package com.chj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chj.entity.ProductCategory;
import com.chj.mapper.ProductCategoryMapper;
import com.chj.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chj.service.ProductService;
import com.chj.vo.ProductCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductService productService;

    @Override
    public List<ProductCategoryVO> findAll() {
        //查询出所有的一级,二级,三级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type", 1);
        //一级分类对应实体类数据
        List<ProductCategory> levelOneList = this.productCategoryMapper.selectList(wrapper);
        //转为VO对象
        List<ProductCategoryVO> levelOneVOList = new ArrayList<>();
        int i = 0;
        for (ProductCategory productCategory : levelOneList) {
            ProductCategoryVO oneVO = new ProductCategoryVO();
            oneVO.setId(productCategory.getId());
            oneVO.setName(productCategory.getName());
            levelOneVOList.add(oneVO);
            //找到属于我的二级分类
            wrapper = new QueryWrapper();
            wrapper.eq("type", 2);
            wrapper.eq("parent_id", oneVO.getId());
            //二级分类对应实体类数据
            List<ProductCategory> levelTwoList = this.productCategoryMapper.selectList(wrapper);
            //转为VO对象
            List<ProductCategoryVO> levelTwoVOList = new ArrayList<>();
            for (ProductCategory productCategory2 : levelTwoList) {
                ProductCategoryVO twoVO = new ProductCategoryVO();
                twoVO.setId(productCategory2.getId());
                twoVO.setName(productCategory2.getName());
                levelTwoVOList.add(twoVO);
                //三级分类对应实体类数据
                wrapper = new QueryWrapper();
                wrapper.eq("type", 3);
                wrapper.eq("parent_id", twoVO.getId());
                List<ProductCategory> levelThreeList = this.productCategoryMapper.selectList(wrapper);
                //转为VO对象
                List<ProductCategoryVO> levelThreeVOList = new ArrayList<>();
                for (ProductCategory productCategory3 : levelThreeList) {
                    ProductCategoryVO threeVO = new ProductCategoryVO();
                    threeVO.setId(productCategory3.getId());
                    threeVO.setName(productCategory3.getName());
                    levelThreeVOList.add(threeVO);
                }
                twoVO.setChildren(levelThreeVOList);
            }
            oneVO.setChildren(levelTwoVOList);
            oneVO.setBannerImg("banner"+i+".png");
            oneVO.setTopImg("top"+i+".png");
            i++;
            //查询商品
            oneVO.setProducts(this.productService.findByLevle(1, oneVO.getId()));
        }
        return levelOneVOList;
    }
}
