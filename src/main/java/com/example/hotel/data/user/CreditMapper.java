package com.example.hotel.data.user;

import com.example.hotel.po.Credit;
import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CreditMapper {

    /**
     * 添加一条信用记录     //(考虑一下要不要新建一个Credit类，感觉creditmapper只有内部会调用，感觉也没有太大的必要）
     *
     * @return
     */
    int addCreditRecord(Credit credit);
    //int addCreditRecord(@Param("change_time") LocalDateTime change_time, @Param("user_id") int user_id, @Param("order_id") int order_id, @Param("actionType") String actionType, @Param("credit_change") int credit_change, @Param("credit_result") int credit_result);

    /**
     * 删除信用记录
     * @param orderId 信用记录ID
     * @return
     * **/
    int rmCreditRecord(@Param("orderId") Integer orderId);

    /**
     * 查询某用户的所有信用记录
     * @param userId
     * @return
     */
    List<Credit> selectByUserId(@Param("userId") Integer userId);
}
