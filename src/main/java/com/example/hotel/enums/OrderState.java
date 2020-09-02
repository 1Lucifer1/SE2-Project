package com.example.hotel.enums;

public enum OrderState {
    Reverse("已预订" ),

    Cancel( "已撤销" ),

    CheckIn("已入住"),

    CheckOut("已退房"),

    Error("异常订单");

    private String value;

    OrderState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
