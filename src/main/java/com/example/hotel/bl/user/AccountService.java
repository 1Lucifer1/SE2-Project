package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.apache.ibatis.annotations.Param;

public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 获取用户名
     * @param id
     * @return
     */
    String getNameById(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String password,String username,String phonenumber);


    /**
     * 更新信用值
     * @param id
     * @param credit
     * @return
     */
    void updateCredit(int id,double credit);

    /**
     * 根据用户名查找账号
     * @param email
     * @return
     */
    User getAccountByEmail(String email);
}
