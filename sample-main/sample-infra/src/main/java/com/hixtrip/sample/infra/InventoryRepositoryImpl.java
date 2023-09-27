package com.hixtrip.sample.infra;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl extends ServiceImpl<OrderMapper, Order> implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Integer getInventory(String skuId) {
        Integer number = (Integer) redisTemplate.opsForValue().get(skuId);
        return number;
    }

    @Override
    public boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        redisTemplate.opsForValue().set(skuId, "sellableQuantity", sellableQuantity);
        redisTemplate.opsForValue().set(skuId, "withholdingQuantity", withholdingQuantity);
        redisTemplate.opsForValue().set(skuId, "occupiedQuantity", occupiedQuantity);
        return true;
    }


}
