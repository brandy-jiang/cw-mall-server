package com.chenwang.mall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedHeaders(new String[] { "*" })
//				.allowedMethods(new String[] { "*" })
//				.allowedOrigins(new String[] { "*" });
//	}
//@Override
//public void configurePathMatch(PathMatchConfigurer configurer) {
//  super.configurePathMatch(configurer);
//
//  configurer.setUseSuffixPatternMatch(false);
//}
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    "classpath:/META-INF/resources/", "classpath:/resources/",
//    "classpath:/static/", "classpath:/public/","file:/Users/jimmylee/qrcode/" };
    "classpath:/static/", "classpath:/public/","file:/d:/java/bc/qrcode/" };

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
        .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
  }
	@Bean
  RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
