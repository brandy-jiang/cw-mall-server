package com.chenwang.mall.common.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
  Page<T> findAll(Specification<T> s, Pageable page);
  List<T> findAll(Specification<T> s);
  Long count(Specification<T> s);
}
