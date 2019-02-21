package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.CommentRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.model.user.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseService<Comment, CommentRepository> {
}
