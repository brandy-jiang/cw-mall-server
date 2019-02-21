package com.chenwang.mall.baicai.dao;

import com.chenwang.mall.common.base.BaseRepository;
import com.chenwang.mall.model.payment.UserBalance;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends BaseRepository<UserBalance, Long> {
}
