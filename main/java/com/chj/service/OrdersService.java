package com.chj.service;

import com.chj.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chj.entity.User;
import com.chj.vo.OrdersVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
public interface OrdersService extends IService<Orders> {
    public Integer create(String selectAddress, String address, String remark, User user, Float cost);
    public List<OrdersVO> findVOByUserId(Integer userId);
}
