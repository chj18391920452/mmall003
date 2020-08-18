package com.chj.service;

import com.chj.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
public interface OrderDetailService extends IService<OrderDetail> {

    public List<OrderDetail> getByUserId(Integer userId,Integer orderId);

}
