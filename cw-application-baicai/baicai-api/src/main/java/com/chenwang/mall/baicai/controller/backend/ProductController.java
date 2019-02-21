package com.chenwang.mall.baicai.controller.backend;

import com.chenwang.mall.baicai.service.ProductService;
import com.chenwang.mall.common.base.BaseController;
import com.chenwang.mall.dto.PageDto;
import com.chenwang.mall.dto.backend.ProductDto;
import com.chenwang.mall.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/backend/product")
@Slf4j
public class ProductController extends BaseController {
  @Autowired
  ProductService productService;
  @RequestMapping(value="list",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PageDto<ProductDto> listB(ProductDto dto, HttpServletRequest req){
    log.info(" request list {}", dto);
//    BannerDto user = (BannerDto) req.getSession().getAttribute("customer");
    Page<Product> page = productService.findAll(productService.where("status").eq(1)
        .and("title").like(dto.getName())
        .searchCustomized(), (dto.getPage() - 1) * PAGE_SIZE, PAGE_SIZE, DEFAULT_SORT);
    List<ProductDto> result = ProductDto.convert(page.getContent());
    PageDto pd = new PageDto();
    pd.setCount(page.getTotalElements());
    pd.setData(result);
    log.info("{}", pd);
    return pd;
  }

  @RequestMapping(value="add",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String add(ProductDto dto){
    log.info(" request add");
    try {
      productService.addProduct(dto);
      return "{\"msg\":\"success\"}";
      //resp.sendRedirect(java.net.URLEncoder.encode("user_list.html", "utf-8"));
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"添加产品失败\"}";
    }

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
  @RequestMapping(value="del",method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String del(ProductDto dto){
    log.info(" request del");
    try {
      productService.deleteById(dto.getId());
      return "{\"msg\":\"success\"}";
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"msg\":\"error\"}";
    }
  }
}
