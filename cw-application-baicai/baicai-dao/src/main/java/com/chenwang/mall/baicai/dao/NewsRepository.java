package com.chenwang.mall.baicai.dao;

import com.chenwang.mall.common.base.BaseRepository;
import com.chenwang.mall.model.content.News;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends BaseRepository<News, Long> {
}
