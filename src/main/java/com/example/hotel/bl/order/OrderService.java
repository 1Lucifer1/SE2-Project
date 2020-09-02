package com.example.hotel.bl.order;

import com.example.hotel.enums.OrderState;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得酒店管理人员管理的酒店下的所有订单
     * @param userId
     * @return
     */
    List<Order> getManageOrders(int userId);

    /**
     * 获得指定用户的所有订单信息
     * @param userId
     * @return
     */
    List<Order> getUserOrders(int userId);

    /**
     * 撤销订单
     * @param orderId
     * @return
     */
    ResponseVO annulOrder(int orderId);

    //change
    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    //personal
    /**
     * 查看某房间的所有订单
     * @param roomId
     * @return
     */
    List<Order> getRoomOrders(Integer roomId);

    /**
     *订单check in
     * @param orderId 酒店Id
     * @return 是否成功
     * **/
    ResponseVO orderCheckIn(Integer orderId);

    /**
     *订单check out
     * @param orderId 酒店Id
     * @return 是否成功
     * **/
    ResponseVO orderCheckOut(Integer orderId);

    /**
     * 如果时间在超过最晚订单执行时间后还没有办理入住，系统自动将其置为异常订单
     * @param orderId 酒店Id
     * @return 是否成功
     * **/
    ResponseVO errorOrder(Integer orderId);

    /**
     *删除错误订单，并返回信用值
     * **/
    ResponseVO rmErrorOrder(Integer orderId);

    /**
     * 按订单号查询订单
     * @param orderId
     * @return order
     * **/
    ResponseVO getOrderByOrderId(Integer orderId);


    /**
     * 每天00:00:00定时进行超时未入住的订单的异常设置
     */
    void timingErrorOrder();


    /**
     * 获取某用户有过订单的所有hotel
     * @param userId
     * @return
     */
    List<Integer> getAllOrderedHotelId(Integer userId);

    /**
     * 获取指定用户有正常订单的hotel
     * @param userId
     * @return
     */
    List<Integer> getNormalOrderedHotelId(Integer userId);

    /**
     * 获取指定用户有异常订单的hotel
     * @param userId
     * @return
     */
    List<Integer> getErrorOrderedHotelId(Integer userId);

    /**
     * 获取指定用户有撤销订单的hotel
     * @param userId
     * @return
     */
    List<Integer> getCancelOrderedHotelId(Integer userId);
}
