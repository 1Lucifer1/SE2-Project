package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {


    private final  TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    private final  TimeCouponStrategyImpl timeCouponStrategy;

    //personal
    private final MultipleRoomCouponStrategyImpl multipleRoomCouponStrategy;
    private final BirthdayCouponStrategyImpl birthdayCouponStrategy;

    private final CouponMapper couponMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();  //暂时只有 TargetMoneyCouponStrategy 和 TimeCouponStrategy 2个 (后为 3个，添加了 MultipleRoomCouponStrategy )


    // ???????????????????????????这边搞个构造函数干啥的？？？？？？？？？？？？？？？？
    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             MultipleRoomCouponStrategyImpl multipleRoomCouponStrategy, //personal 还不知道这个 自动装配 和 bean 的用处
                             BirthdayCouponStrategyImpl birthdayCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);

        //personal
        this.multipleRoomCouponStrategy=multipleRoomCouponStrategy;
        strategyList.add(multipleRoomCouponStrategy);
        this.birthdayCouponStrategy=birthdayCouponStrategy;
        strategyList.add(birthdayCouponStrategy);
    }



    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {     // 暂时长度为2 + 2
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {       // 传入的coupon为已筛选为本hotel的coupon
                    availAbleCoupons.add(hotelCoupons.get(i));
                    break;
                }
            }
        }

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }

    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }


    @Override
    public CouponVO addHotelBirthdayCoupon(HotelBirthdayCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setCouponType(couponVO.getType());
        coupon.setDescription(couponVO.getDescription());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setStatus(1);
        int result=couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelMultipleRoomCoupon(HotelMultipleRoomCouponVO couponVO) {
        Coupon coupon=new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setCouponType(couponVO.getType());
        coupon.setDescription(couponVO.getDescription());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setStatus(1);
        int result=couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelTimeCoupon(HotelTimeCouponVO couponVO) {
        Coupon coupon=new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setCouponType(couponVO.getType());
        coupon.setDescription(couponVO.getDescription());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setStartTime(couponVO.getStartTime());
        coupon.setEndTime(couponVO.getEndTime());
        coupon.setStatus(1);
        int result=couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }
}
