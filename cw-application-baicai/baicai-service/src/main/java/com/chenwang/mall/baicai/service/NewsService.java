package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.NewsRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.dto.backend.NoticeDto;
import com.chenwang.mall.model.content.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class NewsService extends BaseService<News, NewsRepository> {
  @Autowired
  NewsRepository newsRepository;
  @Transactional
  public void addNews(NoticeDto dto) {
    if(dto.getId() != null){
      updateNews(dto);
      return;
    }
    News news = new News();
    news.setTitle(dto.getTitle());
    news.setContent(dto.getTitle());
    news.setSort(dto.getId().intValue());
    news.setCreateTime(new Date());
    news.setStatus(1);
    news.setAuthor(dto.getAuthor());
    save(news);
  }

  private void updateNews(NoticeDto dto) {
    News news = findOne(dto.getId());
    news.setContent(dto.getContent());
    news.setTitle(dto.getTitle());
    news.setUpdateTime(new Date());
    news.setAuthor(dto.getAuthor());
  }

}
