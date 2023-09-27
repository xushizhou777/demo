package com.hixtrip.sample.domain.order.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository extends IService<Order> {
    public void createOrder(Order order);
        //需要你在infra实现, 自行定义出入参


    /**
     * todo 需要实现
     * 待付款订单支付成功
     *
     * @return
     */
    public boolean orderPaySuccess(CommandPay commandPay) ;
        //需要你在infra实现, 自行定义出入参



    /**
     * todo 需要实现
     * 待付款订单支付失败
     *
     * @return
     */
    public boolean orderPayFail(CommandPay commandPay);
        //需要你在infra实现, 自行定义出入参

}
