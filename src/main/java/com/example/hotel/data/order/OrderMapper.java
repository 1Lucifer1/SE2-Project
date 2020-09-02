package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getHotelOrders(@Param("hotelId") int hotelId);

    List<Order> getUserOrders(@Param("userId") int userId);

    int annulOrder(@Param("orderId") int orderId, @Param("cancellationDate") String cancellationDate);

    Order getOrderById(@Param("orderId") int orderId);

    //personal
    List<Order> getRoomOrders(@Param("roomId") int roomId);

    /**
     * update the order in database
     * @param orderId the order key
     * @param state new state for this order
     * **/
    int updateState(@Param("orderId") int orderId, @Param("state") String state);

    int updateCheckInTime(@Param("orderId") int orderId, @Param("updateTime") String time);

    int updateCheckOutTime(@Param("orderId") int orderId, @Param("updateTime") String time);

    int updateErrorTime(@Param("orderId") int orderId, @Param("updateTime") String time);

    List<Integer> getAllOrderedHotelId(@Param("userId") Integer userId);

    List<Integer> getNormalOrderedHotelId(@Param("userId") Integer userId);

    List<Integer> getErrorOrderedHotelId(@Param("userId") Integer userId);

    List<Integer> getCancelOrderedHotelId(@Param("userId") Integer userId);

}
