package com.chenwang.mall.baicai.dao;

import com.chenwang.mall.common.base.BaseRepository;
import com.chenwang.mall.model.order.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
}
