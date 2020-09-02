package com.example.hotel.vo;

public class HotelMultipleRoomCouponVO extends CouponVO{
    private Integer hotelId;

    private Integer targetMoney;

    private Integer discountMoney;

    private double discount;

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
}
