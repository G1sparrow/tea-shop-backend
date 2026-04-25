package com.teashop.mapper;

import com.teashop.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User findById(@Param("id") Long id);

    User findByUsername(@Param("username") String username);

    List<User> findAll();

    int insert(User user);

    int update(User user);

    int deleteById(@Param("id") Long id);

    boolean existsByUsername(@Param("username") String username);

    Long countAll();
}
