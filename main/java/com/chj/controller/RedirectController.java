package com.chj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 映射页面
 * 直接访问静态页面,thymeleaf不会解析
 * 必须要通过后台映射才可以解析
 */
@Controller
public class RedirectController {

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return url;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/productCategory/findAll";
    }

}
