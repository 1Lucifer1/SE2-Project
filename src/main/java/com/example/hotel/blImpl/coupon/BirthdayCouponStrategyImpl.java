package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    AccountService accountService;

    /**
     * 判断某个订单是否满足某种 生日优惠
     * @param orderVO
     * @param coupon
     * @return
     */

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            if (coupon.getStatus() == 1 && coupon.getCouponType() == 1) {     // 有效 + 种类

                User user=accountService.getUserInfo(orderVO.getUserId());

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String curdate = sf.format(date);

                if(curdate.substring(6).equals(user.getBirthday().substring(6))){       //日期
                    if (orderVO.getPrice() >= coupon.getTargetMoney()) {          //门槛
                        return true;
                    }
                }

            }

            return false;
        }
        catch (Exception e){
            return false;
        }
    }
}
