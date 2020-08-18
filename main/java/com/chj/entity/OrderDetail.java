package com.chj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
@Data
@NoArgsConstructor
  @EqualsAndHashCode(callSuper = false)
    public class OrderDetail implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 订单主键
     */
      private Integer orderId;

      /**
     * 商品主键
     */
      private Integer productId;

      /**
     * 数量
     */
      private Integer quantity;

      /**
     * 消费
     */
      private Float cost;

  public OrderDetail(Integer orderId, Integer productId, Integer quantity, Float cost) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.cost = cost;
  }
}
