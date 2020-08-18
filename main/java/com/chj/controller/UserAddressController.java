package com.chj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chj.entity.User;
import com.chj.enums.ResultEnum;
import com.chj.exception.MallException;
import com.chj.service.CartService;
import com.chj.service.UserAddressService;
import com.chj.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("//userAddress")
@Slf4j
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private CartService cartService;

    @GetMapping("/manage")
    public ModelAndView manage(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("未登录，user={}", user);
            throw new MallException(ResultEnum.NOT_LOGIN);
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("list",this.userAddressService.list(wrapper));
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        modelAndView.addObject("carts",cartVOS);
        return modelAndView;
    }

}

