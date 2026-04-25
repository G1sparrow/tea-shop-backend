package com.teashop.mapper;

import com.teashop.common.entity.Product;
import com.teashop.common.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product findById(@Param("id") Long id);

    List<Product> findAll();

    List<Product> findByStatusTrue();

    List<Product> findByCategory(@Param("category") ProductCategory category);

    List<Product> findByStatusTrueAndKeyword(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int countByStatusTrue();

    int countByStatusTrueAndKeyword(@Param("keyword") String keyword);

    int insert(Product product);

    int update(Product product);

    int deleteById(@Param("id") Long id);

    Long countAll();

    int delete(Long id);
}
