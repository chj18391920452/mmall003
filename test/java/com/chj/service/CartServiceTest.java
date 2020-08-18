package com.chj.service;

import com.chj.vo.OrdersVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrdersService ordersService;
    @Test
    void test(){
        this.cartService.findByUserId(44);
    }

    @Test
    void test2(){
        List<OrdersVO> voByUserId = this.ordersService.findVOByUserId(44);
        int i = 0;
    }
}