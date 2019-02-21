package com.chenwang.mall.baicai.dao;

import com.chenwang.mall.common.base.BaseRepository;
import com.chenwang.mall.model.user.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Long> {
}
