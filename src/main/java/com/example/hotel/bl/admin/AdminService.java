package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;

import java.util.List;


public interface AdminService {

    /**
     * 添加酒店管理人员账号
     * @param userForm
     * @return
     */
    ResponseVO addManager(UserForm userForm);

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();

    //personal

    /**
     * 获得所有普通用户的信息
     * @return
     */
    List<User> getAllClients();


    /**
     * 删除指定的（酒店管理人员）各种用户
     * @param userId
     * @return
     */
    ResponseVO deleteUser(int userId);


    /**
     * 信用充值
     * @param rechargePoint 充值的信用值
     * @param userId
     * @return
     */
    ResponseVO rechargeCredit(int rechargePoint,int userId);
}
