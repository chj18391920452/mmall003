package com.chj.service;

import com.chj.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chj.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findByUserId(Integer userId);
}
