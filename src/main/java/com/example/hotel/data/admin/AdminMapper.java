package com.example.hotel.data.admin;

import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    int addManager(User user);

    List<User> getAllManagers();

    List<User> getAllClients();

    int deleteUser(@Param("id") Integer userId);        //为什么返回int？？？？？？？？？
}
