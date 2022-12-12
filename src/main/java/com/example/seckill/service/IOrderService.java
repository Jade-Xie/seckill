package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xieys
 * @since 2022-12-10
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goodsVo);


}
