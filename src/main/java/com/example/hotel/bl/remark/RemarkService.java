package com.example.hotel.bl.remark;

/**
 * @Author: LuZhangchi
 * @Date: 2020-06-07
 */

import com.example.hotel.po.Remark;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.RemarkVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface RemarkService {
    /**
     * 获取某个酒店的全部评论给信息
     * @param hotelId
     * @return
     */
    List<Remark> retrieveHotelRemark(Integer hotelId);

    /**
     * 获取某个用户的全部评论给信息
     * @param userId
     * @return
     */
    List<Remark> retrieveUserRemark(Integer userId);

    /**
     * 用户添加评论
     * @param remark
     * @return
     */
    void addHotelRemark(Remark remark)  throws ServiceException;

}
