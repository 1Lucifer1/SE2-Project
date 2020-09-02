package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        System.out.println("进入优惠券了");
        System.out.println(hotelTargetMoneyCouponVO.getHotelId());

        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    //personal
    @PostMapping("/hotelBirthdayCoupon")
    public ResponseVO addHotelBirthdayCoupon(@RequestBody HotelBirthdayCouponVO hotelBirthdayCouponVO){

        CouponVO couponVO=couponService.addHotelBirthdayCoupon(hotelBirthdayCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelMultipleRoomCoupon")
    public ResponseVO addHotelMultipleRoomCoupon(@RequestBody HotelMultipleRoomCouponVO hotelMultipleRoomCouponVO){

        CouponVO couponVO=couponService.addHotelMultipleRoomCoupon(hotelMultipleRoomCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelTimeCoupon")
    public ResponseVO addHotelTimeCoupon(@RequestBody HotelTimeCouponVO hotelTimeCouponVO){

        CouponVO couponVO=couponService.addHotelTimeCoupon(hotelTimeCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }



    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


}
