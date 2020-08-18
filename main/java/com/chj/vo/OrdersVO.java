package com.chj.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdersVO {
    private String loginName;
    private String serialnumber;
    private String userAddress;
    private Float cost;
    private List<OrderDetailVO> orderDetailVOS;
}
