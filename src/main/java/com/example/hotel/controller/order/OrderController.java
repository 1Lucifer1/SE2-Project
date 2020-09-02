package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.enums.OrderState;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userId}/getManagerOrders")
    public ResponseVO retrieveManageOrders(@PathVariable int userId) {
        //System.out.println("jinruhouduanle");
        //System.out.println(userId);
        return ResponseVO.buildSuccess(orderService.getManageOrders(userId));
    }

    @GetMapping("/{userId}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userId){
//        System.out.println("进入后端了");
//        System.out.println(userId);
        return ResponseVO.buildSuccess(orderService.getUserOrders(userId));
    }

    @GetMapping("/{orderId}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderId){
        return orderService.annulOrder(orderId);
    }


    //change
    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

    @PostMapping("/{orderId}/orderCheckIn")
    public ResponseVO orderCheckIn(@PathVariable Integer orderId){
        return orderService.orderCheckIn(orderId);
    }

    @PostMapping("/{orderId}/orderCheckOut")
    public ResponseVO orderCheckOut(@PathVariable Integer orderId){
        return orderService.orderCheckOut(orderId);
    }

    //这个暂时不用同步到前端
    @PostMapping("/{orderId}/errorOrder")
    public ResponseVO errorOrder(@PathVariable Integer orderId){
        return orderService.errorOrder(orderId);
    }

    @PostMapping("/{orderId}/rmErrorOrder")
    public ResponseVO rmErrorOrder(@PathVariable Integer orderId){
        return orderService.rmErrorOrder(orderId);
    }

    @GetMapping("/{orderId}/getOrderByOrderId")
    public ResponseVO getOrderByOrderId(@PathVariable int orderId){
        return orderService.getOrderByOrderId(orderId);
    }
}
