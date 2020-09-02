package com.example.hotel.UnitTest.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.controller.coupon.CouponController;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CouponControllerTest {
    @Autowired
    CouponService couponService;
    @Autowired
    CouponController couponController;
    @Autowired
    HotelMapper hotelMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    EncryptionService encryptionService;

    private User manager;
    private User user;
    private Hotel hotel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //init a manager
        manager = new User();
        manager.setEmail("1234@qq.com");
        manager.setPassword("12345678");
        adminMapper.addManager(manager);
        manager.setId(adminMapper.getAllManagers().get(adminMapper.getAllManagers().size() - 1).getId());

        //init a hotel
        hotel = new Hotel();
        hotel.setAddress("123");
        hotel.setBizRegion(BizRegion.XiDan);
        hotel.setDescription("321");
        hotel.setHotelName("1234");
        hotel.setHotelStar(HotelStar.Five);
        hotel.setManagerId(manager.getId());
        hotel.setPhoneNum("123");
        hotel.setRate(1.0);
        hotelMapper.insertHotel(hotel);
        hotel.setId(hotelMapper.selectAllHotel().get(hotelMapper.selectAllHotel().size() - 1).getId());

        //init an user
        user = new User();
        user.setEmail("12345@qq.com");
        user.setPassword("12345678");
        user.setUserType(UserType.Client);
        user.setBirthday("2000-01-01");
        user.setCredit(100);
        user.setUserName("aaa");
        user.setPhoneNumber("123");
        accountMapper.createNewAccount(user);
        user.setId(accountMapper.getAccountByEmail(user.getEmail()).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddHotelTargetMoneyCoupon() throws Exception {
        HotelTargetMoneyCouponVO couponVO = new HotelTargetMoneyCouponVO();
        couponVO.setDiscountMoney(100);
        couponVO.setHotelId(hotel.getId());
        couponVO.setTargetMoney(1000);
        couponVO.setDescription("123");
        couponVO.setName("123");
        couponVO.setType(3);
        couponVO.setStatus(1);
        ResponseVO result = couponController.addHotelTargetMoneyCoupon(couponVO);
        assert (result.getSuccess());
        //CouponVO couponVO = (CouponVO) result.getContent();
        List<Coupon> coupons = couponMapper.selectByHotelId(hotel.getId());
        List<Coupon> res = new ArrayList<>();
        for(int i = 0 ; i < coupons.size(); i++){
            if(coupons.get(i).getHotelId().equals(hotel.getId())){
                res.add(coupons.get(i));
            }
        }
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);

    }

    @Test
    @Transactional
    @Rollback
    public void testAddHotelBirthdayCoupon() throws Exception {
        HotelBirthdayCouponVO couponVO = new HotelBirthdayCouponVO();
        couponVO.setDiscountMoney(100);
        couponVO.setHotelId(hotel.getId());
        couponVO.setTargetMoney(1000);
        couponVO.setDescription("123");
        couponVO.setName("123");
        couponVO.setType(1);
        couponVO.setStatus(1);
        ResponseVO result = couponController.addHotelBirthdayCoupon(couponVO);
        assert (result.getSuccess());
        //CouponVO couponVO = (CouponVO) result.getContent();
        List<Coupon> coupons = couponMapper.selectByHotelId(hotel.getId());
        List<Coupon> res = new ArrayList<>();
        //System.out.println(hotel.getId());
        for(int i = 0 ; i < coupons.size(); i++){
            if(coupons.get(i).getHotelId().equals(hotel.getId())){
                res.add(coupons.get(i));
            }
        }
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
    }

    @Test
    @Transactional
    @Rollback
    public void testAddHotelMultipleRoomCoupon() throws Exception {
        HotelMultipleRoomCouponVO couponVO = new HotelMultipleRoomCouponVO();
        couponVO.setDiscountMoney(100);
        couponVO.setDiscount(100.0);
        couponVO.setHotelId(hotel.getId());
        couponVO.setTargetMoney(1000);
        couponVO.setDescription("123");
        couponVO.setName("123");
        couponVO.setType(2);
        couponVO.setStatus(1);
        ResponseVO result = couponController.addHotelMultipleRoomCoupon(couponVO);
        assert (result.getSuccess());
        //CouponVO couponVO = (CouponVO) result.getContent();
        List<Coupon> coupons = couponMapper.selectByHotelId(hotel.getId());
        List<Coupon> res = new ArrayList<>();
        //System.out.println(hotel.getId());
        for(int i = 0 ; i < coupons.size(); i++){
            if(coupons.get(i).getHotelId().equals(hotel.getId())){
                res.add(coupons.get(i));
            }
        }
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
    }

    @Test
    @Transactional
    @Rollback
    public void testAddHotelTimeCoupon() throws Exception {
        HotelTimeCouponVO couponVO = new HotelTimeCouponVO();
        couponVO.setDiscountMoney(100);
        couponVO.setDiscount(100.0);
        couponVO.setHotelId(hotel.getId());
        couponVO.setTargetMoney(1000);
        couponVO.setDescription("123");
        couponVO.setName("123");
        couponVO.setType(4);
        couponVO.setStatus(1);
        couponVO.setStartTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis())));
        couponVO.setEndTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis())));
        ResponseVO result = couponController.addHotelTimeCoupon(couponVO);
        assert (result.getSuccess());
        //CouponVO couponVO = (CouponVO) result.getContent();
        List<Coupon> coupons = couponMapper.selectByHotelId(hotel.getId());
        List<Coupon> res = new ArrayList<>();
        //System.out.println(hotel.getId());
        for(int i = 0 ; i < coupons.size(); i++){
            if(coupons.get(i).getHotelId().equals(hotel.getId())){
                res.add(coupons.get(i));
            }
        }
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
    }

    @Test
    @Transactional
    @Rollback
    public void testGetHotelAllCoupons() throws Exception {
        ResponseVO result = couponController.getHotelAllCoupons(hotel.getId());
        assert result.getSuccess();
        List<Coupon> coupons = (List)result.getContent();
        for(int i = 0 ; i < coupons.size() ; i++){
            if(!coupons.get(i).getHotelId().equals(-1) && !coupons.get(i).getHotelId().equals(hotel.getId())) assert false;
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testGetOrderMatchCoupons() throws Exception {
        ResponseVO result = couponController.getOrderMatchCoupons(user.getId(), hotel.getId(), 1000.0, 1, "2000-01-01", "2020-01-01");
        assert result.getSuccess();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme