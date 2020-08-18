package com.chj.controller;


import com.chj.entity.User;
import com.chj.enums.GenderEnum;
import com.chj.enums.ResultEnum;
import com.chj.exception.MallException;
import com.chj.service.CartService;
import com.chj.service.UserService;
import com.chj.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("//user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @PostMapping("/login")
    public String login(User user, HttpSession session){
        User result = this.userService.login(user);
        String url = "";
        if(result == null){
            url = "login";
        }else{
            session.setAttribute("user", result);
            url = "redirect:/";
        }
        return url;
    }

    @PostMapping("/register")
    public String register(User user){
        if(user.getSex() == 0){
            user.setGender(GenderEnum.FEMALE);
        }
        if(user.getSex() == 1){
            user.setGender(GenderEnum.MALE);
        }
        boolean save = this.userService.save(user);
        if(save){
            return "login";
        }else{
            throw new MallException(ResultEnum.REGISTER_ERROR);
        }
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("未登录，user={}", user);
            throw new MallException(ResultEnum.NOT_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        List<CartVO> cartVOS = this.cartService.findByUserId(user.getId());
        modelAndView.addObject("carts",cartVOS);
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

}

