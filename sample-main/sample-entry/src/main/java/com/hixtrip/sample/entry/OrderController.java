package com.hixtrip.sample.entry;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public String order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        //登录信息可以在这里模拟
        var userId = "";

        return "";
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        if (commandPayDTO.getPayStatus().equals(1)) {
            CommandPay commandPay = new CommandPay(commandPayDTO.getOrderId(), commandPayDTO.getPayStatus());
            boolean b = orderRepository.orderPaySuccess(commandPay);
            if (b) {
                return "支付成功";
            } else {
                return "重复支付";
            }
        } else {
            CommandPay commandPay = new CommandPay(commandPayDTO.getOrderId(), commandPayDTO.getPayStatus());
            orderRepository.orderPayFail(commandPay);
            return "支付失败";
        }

    }

}
