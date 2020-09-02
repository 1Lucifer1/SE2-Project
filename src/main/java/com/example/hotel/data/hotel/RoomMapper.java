package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    // 大修改 roomId 替代 roomType
    //更新的是现存房间数
    int updateRoomInfo(@Param("roomId") Integer roomId,@Param("curNum") Integer curNum);

    int insertRoom(HotelRoom hotelRoom);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    int getRoomCurNum(@Param("roomId") Integer roomId);

    HotelRoom getRoomById(@Param("roomId") Integer roomId);

    int deleteRoomsByHotelId(@Param("hotelId") Integer hotelId);

    int updateRoomTotalNum(@Param("roomId") Integer roomId,@Param("totalChange") Integer totalChange);
}
