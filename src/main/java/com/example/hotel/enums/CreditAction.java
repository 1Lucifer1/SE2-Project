package com.example.hotel.enums;

public enum CreditAction {
    Execute("订单执行"),
    Abnormal("订单异常"),
    Cancel("订单撤销"),
    Invest("充值")
    ;

    private String value;

    CreditAction(String value){ this.value=value; }

    @Override
    public String toString() {
        return value;
    }


}
