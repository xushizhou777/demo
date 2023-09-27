package com.hixtrip.sample.infra;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;


public class OrderRepositoryImpl extends ServiceImpl<OrderMapper, Order> implements OrderRepository {


    @Override
    public void createOrder(Order order) {
        this.save(order);

    }

    @Override
    public boolean orderPaySuccess(CommandPay commandPay) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getId, commandPay.getOrderId());
        Order one = this.getOne(wrapper);
        if (one.getPayStatus().equals(commandPay.getPayStatus())) {
            return false;
        } else {
            one.setPayStatus(commandPay.getPayStatus());
            this.save(one);
            return true;
        }
    }

    @Override
    public boolean orderPayFail(CommandPay commandPay) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getId, commandPay.getOrderId());
        Order one = this.getOne(wrapper);
        one.setPayStatus(commandPay.getPayStatus());
        this.save(one);
        return false;
    }

}
