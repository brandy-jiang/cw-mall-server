package com.chenwang.mall.baicai.controller;

import com.chenwang.mall.baicai.service.ProductService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.CartDto;
import com.chenwang.mall.dto.backend.ProductDto;
import com.chenwang.mall.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class FTProductController extends BaseController {
  @Autowired
  ProductService productService;
  @RequestMapping(value="/listHome",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<ProductDto> listHome(HttpServletRequest req){
    Page<Product> page = productService.findAll(productService.where("isRecommended", "status","shopType").eq(1,1,0).searchCustomized(),0,20, DEFAULT_SORT);
    return ProductDto.convert(page.getContent());
  }

  @RequestMapping(value="/listByKeywords",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<ProductDto> listByKeywords(@RequestParam(name="keywords") String keywords,HttpServletRequest req){
    Page<Product> page = productService.findAll(productService.where( "status","shopType").eq(1,0)
                        .and("title|description").like(keywords).searchCustomized(),0,2000, DEFAULT_SORT);

    return ProductDto.convert(page.getContent());
  }

  @RequestMapping(value="view",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ProductDto view(ProductDto dto){
    log.info(" request view");
    try {
      return ProductDto.convert(productService.findOne(dto.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @RequestMapping(value="add2Cart",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void add2Cart(ProductDto dto, HttpServletRequest req){
    CartDto cartDto = (CartDto) req.getSession().getAttribute("cart");
    if(cartDto == null) {
      cartDto = new CartDto();
      List<Long> ids = new ArrayList<>();
      List<ProductDto> list = new ArrayList<>();
      cartDto.setProductIds(ids);
      cartDto.setProductDtoList(list);
    }
    Product product = productService.findOne(dto.getId());
    int i = cartDto.getProductIds().indexOf(dto.getId());
    if(i >= 0){
      ProductDto productDto = cartDto.getProductDtoList().get(i);
      productDto.setNum(productDto.getNum() + dto.getNum());
    }else{
      cartDto.getProductIds().add(dto.getId());
      ProductDto p = new ProductDto();
      p.setNum(dto.getNum());
      p.setId(dto.getId());
      p.setIsSelected(1);
      p.setName(product.getTitle());
      p.setDescription(product.getDescription());
      p.setPrice(product.getPrice() / 100.);
      cartDto.getProductDtoList().add(p);
    }
    req.getSession().setAttribute("cart", cartDto);
    log.info("cartDto : {}", cartDto);
  }
}
