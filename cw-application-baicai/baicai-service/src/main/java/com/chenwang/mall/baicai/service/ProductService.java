package com.chenwang.mall.baicai.service;

import com.chenwang.mall.baicai.dao.ProductRepository;
import com.chenwang.mall.common.base.BaseService;
import com.chenwang.mall.dto.backend.ProductDto;
import com.chenwang.mall.model.product.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ProductService extends BaseService<Product, ProductRepository> {
  @Transactional
  public void addProduct(ProductDto dto) {
    if(dto.getId() != null){
      updateProduct(dto);
      return;
    }
    Product product = new Product();
    product.setPrice((long)(dto.getPrice() * 100));
    product.setMarketPrice((long)(dto.getMarketPrice() * 100));
    product.setScore(dto.getScore());
    product.setDescription(dto.getDescription());
    product.setCreateTime(new Date());
    product.setStock(dto.getStock());
    product.setTitle(dto.getName());
    product.setStatus(dto.getIntStatus());
    product.setShopType(dto.getType());
    save(product);
  }

  private void updateProduct(ProductDto dto){
    Product product = findOne(dto.getId());
    product.setPrice((long)(dto.getPrice() * 100));
    product.setMarketPrice((long)(dto.getMarketPrice() * 100));
    product.setScore(dto.getScore());
    product.setDescription(dto.getDescription());
    product.setUpdateTime(new Date());
    product.setStock(dto.getStock());
    product.setTitle(dto.getName());
    product.setStatus(dto.getIntStatus());
    product.setShopType(dto.getType());
  }
}
