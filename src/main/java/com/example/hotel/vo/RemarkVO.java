package com.example.hotel.vo;
/**
 * @Author: LuZhangchi
 * @Date: 2020-06-07
 */
public class RemarkVO {
    private Integer id;
    private Integer userId;
    private Integer hotelId;
    private String content;
    private Integer star;
    private String remarkTime;
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(String remarkTime) {
        this.remarkTime = remarkTime;
    }

    public String getUserName(){ return userName; }

    public void setUserName(String userName) { this.userName = userName; }
}
