package com.chenwang.mall.common.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
public class BaseService<T extends IDEntity, K extends BaseRepository<T, Long>> {
  protected Gson gson = new GsonBuilder().serializeNulls().create();
  @Autowired
  K k;
  @Autowired
  protected RedisTemplate<String, String> redisTemplate;
  @Autowired
  protected RestTemplate restTemplate;
  public T findOne(Long id){
    return k.findById(id).get();
  }

  public Page<T> findAll(Specification<T> s, int start, int offset, Sort sort ){
    PageRequest pr = PageRequest.of(start, offset);
    pr.getSortOr(sort);
    return k.findAll(s, pr);
  }
  public Page<T> findAll(Specification<T> s, int start, int offset){
    PageRequest pr = PageRequest.of(start, offset);
    return k.findAll(s, pr);
  }
  public List<T> findAll(Specification<T> s){
    return k.findAll(s);
  }

  public Long count(Specification<T> s){
    return k.count(s);
  }
  @Transactional
  public T save(T t){
    return  k.save(t);
  }
  @Transactional
  public void delete(T t){
    k.delete(t);
  }
  @Transactional
  public void deleteById(Long id) {
    k.deleteById(id);
  }
  @Transactional
  public void batchDelete(Iterable<T> t){
    k.deleteInBatch(t);
  }


  public BaseSpecification<T> where(String ... key){
    return new BaseSpecification<T>().where(key);
  }

}
