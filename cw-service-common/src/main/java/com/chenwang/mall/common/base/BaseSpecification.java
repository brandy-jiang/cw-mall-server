package com.chenwang.mall.common.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

public class BaseSpecification<T> {
  public static final int EQ = 0;
  public static final int NOT_EQ = 1;
  public static final int LIKE = 2;
  public static final int NOT_LIKE = 3;
  public static final int GREATER_THAN = 4;
  public static final int LESS_THAN = 5;
  public static final int GREATER_THAN_EQUAL = 6;
  public static final int LESS_THAN_EQUAL = 7;
  public static final int IS_NULL = 8;
  public static final int NOT_NULL = 9;
  public static final int IN = 10;
  private Map<String , Object> eqMap = new HashMap<>();
  private Map<String , Object> likeMap = new HashMap<>();
  private Map<String , Object> notLikeMap = new HashMap<>();
  private Map<String , Object> geMap = new HashMap<>();
  private Map<String , Object> gtMap = new HashMap<>();
  private Map<String , Object> leMap = new HashMap<>();
  private Map<String , Object> ltMap = new HashMap<>();
  private Map<String , Object> notEqMap = new HashMap<>();
  private Map<String , Object[]> inMap = new HashMap<>();
  private List<String> isNullMap = new ArrayList<>();
  private List<String> isNOtNullMap = new ArrayList<>();
  private List<Specification<T>> orList = new ArrayList<>();

  private String [] lastKeys ;
  public static BaseSpecification getInstance(){
    return new BaseSpecification();
  }

  public Specification<T> searchCustomized( ) {
    return new Specification<T>() {
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                   CriteriaBuilder builder) {
        List<Predicate> predicates1 = buildAll(root, builder);
        return builder.and(predicates1.toArray(new Predicate[predicates1.size()]));
      }
    };
  }
  private List<Predicate> buildAll(Root<T> root, CriteriaBuilder builder){
    List<Predicate> predicates1 = new ArrayList<>();
    buildCustomized(root, builder, eqMap, EQ, predicates1);
    buildCustomized(root, builder, likeMap, LIKE, predicates1);
    buildCustomized(root, builder, notEqMap, NOT_EQ, predicates1);
    buildCustomized(root, builder, notLikeMap, NOT_LIKE,predicates1);
    buildCustomized(root, builder, geMap, GREATER_THAN_EQUAL,predicates1);
    buildCustomized(root, builder, gtMap, GREATER_THAN,predicates1);
    buildCustomized(root, builder, leMap, LESS_THAN_EQUAL,predicates1);
    buildCustomized(root, builder, ltMap, LESS_THAN,predicates1);
    buildCustomized(root, builder, notEqMap, NOT_EQ,predicates1);
    buildCustomized(root, builder, isNullMap, IS_NULL,predicates1);
    buildCustomized(root, builder, isNOtNullMap, NOT_NULL,predicates1);
    buildCustomizedIn(root, builder, inMap, IN,predicates1);

    return predicates1;
  }

  private void buildCustomizedIn(Root<T> root, CriteriaBuilder builder, Map<String, Object[]> map, Integer op, List<Predicate> predicates1){
    if(map != null && map.size() > 0){
      map.forEach((k, v) ->{
        String [] ks = StringUtils.split(k);
        if(ks != null && ks.length > 0){
          switch (op){
            case IN:
              predicates1.add((concatProperty(root, ks).in(v)));
              break;
          }

        }
      });
    }
  }

  private void buildCustomized(Root<T> root, CriteriaBuilder builder, Map<String, Object> map, Integer op, List<Predicate> predicates1){
    if(map != null && map.size() > 0){
      map.forEach((k, v) ->{
        String fields[] = StringUtils.split(k, "|");


        if(fields != null && fields.length == 0){
          String [] ks = StringUtils.split(k, ".");
          switch (op){
            case EQ:
              predicates1.add(builder.equal(concatProperty(root, ks), v));
              break;
            case NOT_EQ:
              predicates1.add(builder.notEqual(concatProperty(root, ks), v));
              break;
            case LIKE:
              predicates1.add(builder.like((Expression<String>) concatProperty(root, ks), v.toString()));
              break;
            case NOT_LIKE:
              predicates1.add(builder.notLike((Expression<String>) concatProperty(root, ks), v.toString()));
              break;
            case GREATER_THAN:
              predicates1.add(builder.greaterThan((Expression<Comparable>)concatProperty(root, ks), (Comparable)v));
              break;
            case LESS_THAN:
              predicates1.add(builder.lessThan((Expression<Comparable>)concatProperty(root, ks), (Comparable)v));
              break;
            case GREATER_THAN_EQUAL:
              predicates1.add(builder.greaterThanOrEqualTo((Expression<Comparable>)concatProperty(root, ks), (Comparable)v));
              break;
            case LESS_THAN_EQUAL:
              predicates1.add(builder.lessThanOrEqualTo((Expression<Comparable>)concatProperty(root, ks), (Comparable)v));
//            default:
//              throw new IllegalAccessException("Operator not support");
          }

        }else{
          List<Predicate> list = new ArrayList<>();
          for(String f : fields) {
            String [] ks = StringUtils.split(f, ".");
            switch (op) {
              case EQ:
                list.add(builder.equal(concatProperty(root, ks), v));
                //predicates1.add(builder.equal(concatProperty(root, ks), v));
                break;
              case NOT_EQ:
                list.add(builder.notEqual(concatProperty(root, ks), v));
                break;
              case LIKE:
                list.add(builder.like((Expression<String>) concatProperty(root, ks), "%" + v.toString() + "%") );
                break;
              case NOT_LIKE:
                list.add(builder.notLike((Expression<String>) concatProperty(root, ks), v.toString()));
                break;
              case GREATER_THAN:
                list.add(builder.greaterThan((Expression<Comparable>) concatProperty(root, ks), (Comparable) v));
                break;
              case LESS_THAN:
                list.add(builder.lessThan((Expression<Comparable>) concatProperty(root, ks), (Comparable) v));
                break;
              case GREATER_THAN_EQUAL:
                list.add(builder.greaterThanOrEqualTo((Expression<Comparable>) concatProperty(root, ks), (Comparable) v));
                break;
              case LESS_THAN_EQUAL:
                list.add(builder.lessThanOrEqualTo((Expression<Comparable>) concatProperty(root, ks), (Comparable) v));
//            default:
//              throw new IllegalAccessException("Operator not support");
            }

          }
          predicates1.add(builder.or(list.toArray(new Predicate[list.size()])));
        }
      });
    }
  }
  private void buildCustomized(Root<T> root, CriteriaBuilder builder, List<String> map, int op, List<Predicate> predicates1){
    if(map != null && map.size() > 0){
      map.forEach(k ->{
        switch (op){
          case IS_NULL:
            predicates1.add(builder.isNull((Expression<String>) concatProperty(root, k)));
            break;
          case NOT_NULL:
            predicates1.add(builder.isNotNull((Expression<String>) concatProperty(root, k)));
            break;
        }
      });
    }
  }



  public void setConditions(String [] keys, Integer operator, Object [] values) throws IllegalAccessException {
    switch (operator){
      case EQ:
        fillConditions(eqMap, keys, values);
        break;
      case NOT_EQ:
        fillConditions(notEqMap, keys, values);
        break;
      case LIKE:
        fillConditions(likeMap, keys, values);
        break;
      case NOT_LIKE:
        fillConditions(notLikeMap, keys, values);
        break;
      case GREATER_THAN:
        fillConditions(gtMap, keys, values);
        break;
      case LESS_THAN:
        fillConditions(ltMap, keys, values);
        break;
      case GREATER_THAN_EQUAL:
        fillConditions(geMap, keys, values);
        break;
      case LESS_THAN_EQUAL:
        fillConditions(leMap, keys, values);
        break;
      default:
        throw new IllegalAccessException("Operator not support");
    }
  }
  public void setConditions(String [] keys, Integer operator) throws IllegalAccessException{
    switch (operator) {
      case IS_NULL:
        fillConditions(isNullMap, keys);
        break;
      case NOT_NULL:
        fillConditions(isNOtNullMap, keys);
        break;
      default:
        throw new IllegalAccessException("Operator not support");
    }
  }

  private void fillConditions(Map<String, Object> map, String []keys , Object values[]){
    for(int i = 0; i < keys.length; i ++){
      if(values[i] != null)
        map.put(keys[i], values[i]);
    }
  }
  private void fillConditions(Map<String, Object[]> map, String []keys , Object values[][]){
    for(int i = 0; i < keys.length; i ++){
      if(values[i] != null)
        map.put(keys[i], values[i]);
    }
  }
  private void fillConditions(List map, String key[]){
    map.addAll(Arrays.asList(key));
  }

  private Path<T> concatProperty(Root<T> root, String ... property){
    if(property == null || property.length == 0)
      return null;

    Path<T> first = root.get(property[0]);
    if(property.length == 1)
      return first;

    for(int i = 1 ; i < property.length ; i ++){
      first = iteratorProperty(first, property[i]);
    }
    return first;

  }
  private Path<T> iteratorProperty(Path<T> p, String property){
    return p.get(property);
  }

  private String firstLetterUpper(String str){
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }



  public BaseSpecification<T> where(String ... keys){
    this.lastKeys = keys;
    return this;
  }
  public BaseSpecification<T> and(String ... keys){
    this.lastKeys = keys;
    return this;
  }
  public BaseSpecification<T> or(BaseSpecification<T> bs){
    this.orList.add(bs.searchCustomized());
    return this;
  }
  public BaseSpecification<T> eq(Object ... values){
    fillConditions(eqMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> notEq(Object ... values){
    fillConditions(notEqMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> like(Object ... values){
    fillConditions(likeMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> notLike(Object ... values){
    fillConditions(notLikeMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> ge(Object ... values){
    fillConditions(geMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> gt(Object ... values){
    fillConditions(gtMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> le(Object ... values){
    fillConditions(leMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> lt(Object ... values){
    fillConditions(ltMap, lastKeys, values);
    return this;
  }
  public BaseSpecification<T> notNull(Object ... values){
    fillConditions(isNOtNullMap, lastKeys);
    return this;
  }
  public BaseSpecification<T> isNull(Object ... values){
    fillConditions(isNullMap, lastKeys);
    return this;
  }
  public BaseSpecification<T> in(Object[] ... values){
    fillConditions(inMap, lastKeys, values);
    return this;
  }
}
