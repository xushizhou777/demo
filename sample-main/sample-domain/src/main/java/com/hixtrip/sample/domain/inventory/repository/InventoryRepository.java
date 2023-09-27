package com.hixtrip.sample.domain.inventory.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface InventoryRepository extends IService<Order> {

 Integer getInventory(String skuId);
 boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);

}
