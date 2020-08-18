package com.chj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chj.entity.Cart;
import com.chj.entity.Product;
import com.chj.entity.User;
import com.chj.entity.UserAddress;
import com.chj.enums.ResultEnum;
import com.chj.exception.MallException;
import com.chj.service.CartService;
import com.chj.service.OrdersService;
import com.chj.service.ProductService;
import com.chj.service.UserAddressService;
import com.chj.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
@RequestMapping("//cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/goToSettlement3/{ordersId}/{userId}")
    public ModelAndView goToSettlement2(@PathVariable("ordersId") Integer ordersId,
                                        @PathVariable("userId") Integer userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("orders",this.ordersService.getById(ordersId));
        List<CartVO> cartVOS = this.cartService.findByUserId(userId);
        modelAndView.addObject("carts",cartVOS);
        return modelAndView;
    }

    @GetMapping("/goToSettlement2")
    public ModelAndView goToSettlement2(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("未登录，user={}", user);
            throw new MallException(ResultEnum.NOT_LOGIN);
        }
        //查询该用户的购物车记录
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        //查询用户对应的地址
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        List<UserAddress> userAddressList = this.userAddressService.list(wrapper);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        modelAndView.addObject("carts",cartVOS);
        modelAndView.addObject("address",userAddressList);
        return modelAndView;
    }


    /**
     * 跳转到购物车页面
     * @param session
     * @return
     */
    @GetMapping("/goToSettlement1")
    public ModelAndView goToSettlement1(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            throw new MallException(ResultEnum.NOT_LOGIN);
        }
        List<CartVO> carts = this.cartService.findByUserId(user.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    /**
     * 添加购物车
     * @param id
     * @param price
     * @param quantity
     * @param session
     * @return
     */
    @GetMapping("/add/{id}/{price}/{quantity}")
    public String add(
            @PathVariable("id") Integer id,
            @PathVariable("price") Float price,
            @PathVariable("quantity") Integer quantity,
            HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                throw new MallException(ResultEnum.NOT_LOGIN);
            }
            Cart cart = new Cart(id,quantity,price*quantity,user.getId());
            //存入购物车
            boolean save = this.cartService.save(cart);
            if(!save){
                throw new MallException(ResultEnum.CART_SAVE_ERROR);
            }
            //减库存
            Product product = this.productService.getById(id);
            if(product == null){
                log.info("商品不存在,prdocut = {}", product);
                throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = product.getStock() - quantity;
            if(result < 0){
                log.info("库存不足，stock={},quantity={}", product.getStock(),quantity);
                throw new MallException(ResultEnum.STOCK_ERROR);
            }
            product.setStock(result);
            boolean update = this.productService.updateById(product);
            if(!update){
                log.info("保存失败，prdocut={}", product);
                throw new MallException(ResultEnum.STOCK_UPDATE_ERROR);
            }
            return "redirect:/cart/goToSettlement1";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新购物车
     * @param id
     * @param quantity
     * @param cost
     * @return
     */
    @PostMapping("/updateCart/{type}/{id}/{productId}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(@PathVariable("type") String type,
                             @PathVariable("id") Integer id,
                             @PathVariable("productId") Integer productId,
                             @PathVariable("quantity") Integer quantity,
                             @PathVariable("cost") Float cost){
        Cart cart = this.cartService.getById(id);
        cart.setQuantity(quantity);
        cart.setCost(cost);
        boolean update = this.cartService.updateById(cart);
        //更新库存
        Product product = this.productService.getById(productId);
        if(product == null){
            log.info("商品不存在,prdocut = {}", product);
            throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        Integer result = null;
        switch (type){
            case "add":
                result = product.getStock() - 1;
                break;
            case "sub":
                result = product.getStock() + 1;
                break;
        }
        if(result < 0){
            log.info("库存不足，stock={},quantity={}", product.getStock(),quantity);
            throw new MallException(ResultEnum.STOCK_ERROR);
        }
        product.setStock(result);
        update = this.productService.updateById(product);
        if(!update){
            log.info("保存失败，prdocut={}", product);
            throw new MallException(ResultEnum.STOCK_UPDATE_ERROR);
        }
        if(update){
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/removeCart/{id}")
    public String removeCart(@PathVariable("id") Integer id){
        if(id == null){
            throw new MallException(ResultEnum.ID_IS_NULL);
        }
        Cart cart = this.cartService.getById(id);
        if(cart == null){
            throw new MallException(ResultEnum.CART_NOT_EXIST);
        }
        this.cartService.removeById(id);
        return "redirect:/cart/goToSettlement1";
    }
}

