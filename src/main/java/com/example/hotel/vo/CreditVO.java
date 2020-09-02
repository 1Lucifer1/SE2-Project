package com.example.hotel.vo;

import java.time.LocalDateTime;

public class CreditVO {

    // 暂时没用到

    private int id;

    private String changeTime;

    private Integer userId;

    private Integer orderId;

    /**
     * 行为类型 1订单执行 2订单异常 3订单撤销 4充值
     */
    private String actionType;

    private double creditChange;

    private double creditResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public double getCreditChange() {
        return creditChange;
    }

    public void setCreditChange(double creditChange) {
        this.creditChange = creditChange;
    }

    public double getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(double creditResult) {
        this.creditResult = creditResult;
    }
}
