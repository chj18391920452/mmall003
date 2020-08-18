package com.chj.controller;


import com.chj.entity.User;
import com.chj.service.CartService;
import com.chj.service.ProductCategoryService;
import com.chj.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("//productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/findAll")
    public ModelAndView findAll(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list",this.productCategoryService.findAll());
        //判断是否登录
        //如果是登录状态，则取出该用户对应的购物车数据
        //如果是未登录状态，返回空
        User user = (User) session.getAttribute("user");
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findByUserId(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

}

