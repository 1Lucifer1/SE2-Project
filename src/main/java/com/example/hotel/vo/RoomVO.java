package com.example.hotel.vo;

import com.example.hotel.enums.RoomType;

public class RoomVO {
    private Integer id;
    private String roomType;
    private Double price;
    /**
     * 现将curNum属性转为当前无人入住的该类型客房数量
     */
    private Integer curNum;
    private Integer total;

    private Integer hotelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer gethotelId() {
        return id;
    }

    public void sethotelId(Integer id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
