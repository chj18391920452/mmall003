package com.chj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chj.entity.User;
import com.chj.service.CartService;
import com.chj.service.ProductCategoryService;
import com.chj.service.ProductService;
import com.chj.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
@Controller
@RequestMapping("//product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/findByCategory/{type}/{id}")
    public ModelAndView findByCategory(@PathVariable("type") Integer type, @PathVariable("id") Integer id, HttpSession session){
        //根据类型和id取值
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("products",this.productService.findByLevle(type, id));
        modelAndView.addObject("list",this.productCategoryService.findAll());
        User user = (User) session.getAttribute("user");
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    @PostMapping("/findByKey")
    public ModelAndView findByKey(String keyWord, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name", keyWord);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("products",this.productService.list(wrapper));
        modelAndView.addObject("list",this.productCategoryService.findAll());
        User user = (User) session.getAttribute("user");
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product",this.productService.getById(id));
        modelAndView.addObject("list",this.productCategoryService.findAll());
        User user = (User) session.getAttribute("user");
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

}

