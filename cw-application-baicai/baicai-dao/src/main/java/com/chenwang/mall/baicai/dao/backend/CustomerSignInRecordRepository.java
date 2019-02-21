package com.chenwang.mall.baicai.dao.backend;

import com.chenwang.mall.common.base.BaseRepository;
import com.chenwang.mall.model.user.CustomerSignInRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSignInRecordRepository extends BaseRepository<CustomerSignInRecord, Long> {
}
