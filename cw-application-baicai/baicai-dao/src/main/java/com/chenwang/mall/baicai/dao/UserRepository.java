package com.chenwang.mall.baicai.dao;

import com.chenwang.mall.common.base.BaseRepository;
import com.chenwang.mall.model.user.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<Customer, Long> {
}
