package com.chj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chj.entity.Cart;
import com.chj.entity.OrderDetail;
import com.chj.mapper.CartMapper;
import com.chj.mapper.OrderDetailMapper;
import com.chj.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<OrderDetail> getByUserId(Integer userId,Integer orderId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        List<Cart> carts = this.cartMapper.selectList(wrapper);
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (Cart cart : carts) {
//            OrderDetail orderDetail = new OrderDetail();
//            BeanUtils.copyProperties(cart, orderDetail);
//            orderDetail.setOrderId(orderId);
//            orderDetails.add(orderDetail);
//        }
        List<OrderDetail> orderDetails = carts.stream().map(e -> new OrderDetail(orderId,e.getProductId(),e.getQuantity(),e.getCost())).collect(Collectors.toList());
        return orderDetails;
    }
}
