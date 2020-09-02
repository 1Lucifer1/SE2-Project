package com.example.hotel.vo;

public class OrderedHotelVO {
    private HotelVO hotelVO;
    private boolean hasNormal=false;
    private boolean hasError=false;
    private boolean hasCancel=false;

    public OrderedHotelVO() {
    }

    public OrderedHotelVO(HotelVO hotelVO, boolean hasNormal, boolean hasError, boolean hasCancel) {
        this.hotelVO = hotelVO;
        this.hasNormal = hasNormal;
        this.hasError = hasError;
        this.hasCancel = hasCancel;
    }

    public HotelVO getHotelVO() {
        return hotelVO;
    }

    public void setHotelVO(HotelVO hotelVO) {
        this.hotelVO = hotelVO;
    }

    public boolean isHasNormal() {
        return hasNormal;
    }

    public void setHasNormal(boolean hasNormal) {
        this.hasNormal = hasNormal;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public boolean isHasCancel() {
        return hasCancel;
    }

    public void setHasCancel(boolean hasCancel) {
        this.hasCancel = hasCancel;
    }
}
