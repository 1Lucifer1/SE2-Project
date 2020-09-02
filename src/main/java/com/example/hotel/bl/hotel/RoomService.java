package com.example.hotel.bl.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelSearchVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    /**
     * 添加酒店客房信息
     * @param hotelRoom
     */
    void insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 更新客房现存房间数量
     * @param roomId
     * @param rooms
     */
    void updateRoomInfo(Integer roomId, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param roomId    (原来是roomType）
     * @return
     */
    int getRoomCurNum(Integer roomId);

    /**
     * 获取某房间的信息
     * @param roomId
     * @return
     */
    HotelRoom getRoomInfo(Integer roomId);

    /**
     * 删除某酒店下的所有房间
     * @param hotelId
     */
    void deleteRoomsByHotelId(Integer hotelId);

    /**
     * 更新客房总数
     * @param roomId
     * @param totalChange
     */
    ResponseVO updateRoomTotalNum(Integer roomId, Integer totalChange);


    /**
     * 根据所给信息查找房间列表
     * @param hotelId
     * @param roomType
     * @param maxPrice
     * @param minPrice
     * @param roomNum
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    List<HotelRoom> searchRoomsByInfo(Integer hotelId,String roomType,Double maxPrice,Double minPrice,Integer roomNum,String checkInDate,String checkOutDate);

    /**
     * 上面方法的优化简易版
     * @return
     */
    boolean hasRoomByInfo(Integer hotelId,String roomType,Double maxPrice,Double minPrice,Integer roomNum,String checkInDate,String checkOutDate);

}
