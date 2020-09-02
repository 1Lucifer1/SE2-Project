package com.example.hotel.bl.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    void addHotel(HotelVO hotelVO) throws ServiceException;


    /**
     * 修改现在剩余的客房信息（curnum）
     * @param roomId
     * @param rooms
     */
    void updateRoomInfo(Integer roomId,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();


    /**
     * 列表获取某用户管理的所有酒店
     * @param userId
     * @return
     */
    List<HotelVO> retrieveHotelsByManagerId(Integer userId);

    /**
     * 列表获取某普通用户的所有预订过的酒店（正常订单、异常订单和撤销订单要分别标记）
     * @param userId
     * @return
     */
    List<OrderedHotelVO> retrieveHotelsByClientOrdered(Integer userId);

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param roomId
     * @return
     */
    int getRoomCurNum(Integer roomId);


    //personal

    /**
     * 得到某房间的信息
     * @param roomId
     * @return
     */
    HotelRoom getRoomInfo(Integer roomId);

    /**
     * 酒店工作人员删除酒店
     * @param hotelId
     * @return
     */
    ResponseVO deleteHotel(Integer hotelId);


    /**
     * 维护好酒店的信息
     * @param hotelInfoVO
     * @param id
     * @return
     */
    ResponseVO updateHotelInfo(HotelInfoVO hotelInfoVO,Integer id);

    /**
     * 更新酒店评分
     * @param id
     * @param rate
     * @return
     */
    ResponseVO updateHotelRate(Integer id, String rate);


    /**
     * 通过提供的酒店信息查找酒店列表
     * @param hotelSearchVO
     * @return
     */
    List<HotelVO> retrieveHotelByInfo(HotelSearchVO hotelSearchVO);


    /**
     * 根据酒店Id查找酒店
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelById(Integer hotelId);

}
