package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author:sjl
 * @Date: 2021/12/25
 * @Time: 15:20
 */
@Repository
public interface UserMapper {

    User Sel(int id);
}
