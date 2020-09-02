package com.example.hotel.enums;

public enum BizRegion {
    XiDan("西单"),
    JueGang("掘港"),
    TongZhou("通州");

    private String value;

    BizRegion(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
