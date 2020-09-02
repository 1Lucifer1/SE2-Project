package com.example.hotel.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HotelTimeCouponVO extends CouponVO{
    private Integer hotelId;

    private Integer targetMoney;

    private Integer discountMoney;

    private double discount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Integer targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.startTime = LocalDateTime.parse(startTime, df);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.endTime = LocalDateTime.parse(endTime, df);
    }
}
