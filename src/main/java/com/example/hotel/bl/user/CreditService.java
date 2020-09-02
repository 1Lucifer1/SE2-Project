package com.example.hotel.bl.user;

import com.example.hotel.po.Credit;
import com.example.hotel.vo.*;

import java.util.List;

public interface CreditService {
    /**
     * 获取用户信用记录
     * @param userId
     * @return
     */
    List<Credit> retrieveCreditRecord(Integer userId);

    /**
     * 添加一条信用记录
     *@param credit
     * @return
     */
    void addCreditRecord(Credit credit);

    /**
     * 删除信用记录
     * @param orderId 信用记录ID
     * @return
     * **/
    void rmCreditRecord(Integer orderId);


}
