package com.example.hotel.IntegrationTest;

import com.example.hotel.controller.admin.AdminController;
import com.example.hotel.controller.coupon.CouponController;
import com.example.hotel.controller.hotel.HotelController;
import com.example.hotel.controller.order.OrderController;
import com.example.hotel.controller.user.AccountController;
import com.example.hotel.enums.*;
import com.example.hotel.po.*;
import com.example.hotel.vo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {
    @Autowired
    AdminController adminController;
    @Autowired
    CouponController couponController;
    @Autowired
    HotelController hotelController;
    @Autowired
    OrderController orderController;
    @Autowired
    AccountController accountController;

    private Order order;
    private User user;
    private User manager;
    private Hotel hotel;
    private HotelRoom hotelRoom;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        //init a manager
        manager = new User();
        manager.setEmail((int) Math.floor(Math.random() * 1000) + "@qq.com");
        manager.setPassword("12345678");
        manager.setUserType(UserType.HotelManager);
        manager.setId(6);

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
        hotel.setId(1);

        //init a room
        hotelRoom = new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setCurNum(100);
        hotelRoom.setPrice(100);
        hotelRoom.setTotal(100);
        hotelRoom.setId(2);

        //init a new user
        user = new User();
        user.setEmail( (int) Math.floor(Math.random() * 1000) + "@qq.com");
        user.setPassword("12345678");
        user.setUserType(UserType.Client);
        user.setBirthday("2000-01-01");
        user.setCredit(100);
        user.setUserName("aaa");
        user.setPhoneNumber("123");
        user.setId(4);
        //System.out.println(user.getId());

        //init a new order
        order = new Order();
        order.setUserId(user.getId());
        order.setHotelId(1);
        order.setHotelName("汉庭酒店");
        order.setCheckInDate((new SimpleDateFormat("yyyy-MM-dd")).format(new Date(System.currentTimeMillis())));
        order.setCheckOutDate("2020-07-12");
        order.setRoomType(RoomType.BigBed.toString());
        order.setRoomId(2);
        order.setRoomNum(1);
        order.setPeopleNum(1);
        order.setHaveChild(false);
        order.setCreateDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis())));
        order.setPrice(100.0);
        order.setClientName("test");
        order.setPhoneNumber("123");
        order.setOrderState(OrderState.Reverse.toString());

    }

    /**
     * user register -> login system -> add remark
     * **/
    @Test
    @Transactional
    @Rollback
    public void testRegisterAndLoginAndAddRemark(){
        register();
        login();
        addRemark();
    }

    /**
     * user register -> login system -> update user information
     * **/
    @Test
    @Transactional
    @Rollback
    public void testRegisterAndLoginAndUpdateInfo(){
        register();
        login();
        updateInfo();
    }

    /**
     * user register -> login system -> recharge credit
     * **/
    @Test
    @Transactional
    @Rollback
    public void testRegisterAndLoginAndRechargeCredit(){
        register();
        login();
        rechargeCredit();
    }

    /**
     * add manager -> delete user
     * **/
    @Test
    @Transactional
    @Rollback
    public void testAddManagerAndDelete(){
        addManager();
        deleteUser();
    }

    /**
     * reserve hotel -> manager check in and check out
     * **/
    @Test
    @Transactional
    @Rollback
    public void testReserveHotelAndOrderCheckInCheckOut(){
        reserveHotel();
        checkIn();
        checkOut();
    }

    /**
     * reserve hotel -> user cancel order
     * **/
    @Test
    @Transactional
    @Rollback
    public void testReserveHotelAndCancelOrder(){
        reserveHotel();
        cancelOrder();
    }

    /**
     * reserve hotel -> manager set error order
     * **/
    @Test
    @Transactional
    @Rollback
    public void testReserveHotelAndErrorOrder(){
        reserveHotel();
        setErrorOrder();
    }

    /**
     * reserve hotel -> manager set error order -> cancel order -> check out
     * **/
    @Test
    @Transactional
    @Rollback
    public void testReserveHotelAndErrorOrderAndRemoveErrorAndCheckOut(){
        reserveHotel();
        setErrorOrder();
        rmErrorOrder();
        checkOut();
    }

    /**
     * add manager -> add hotel -> add room -> update room current number -> update room total number
     * **/
    @Test
    @Transactional
    @Rollback
    public void testAddManagerAndAddHotelAndAddRoomAndUpdateRoomCurNumAndUpdateRoomTotalNum(){
        addManager();
        addHotel();
        addRoom();
        updateRoomCurNum();
        updateRoomTotalNum();
    }

    /**
     * add manager -> add hotel -> add room -> add coupons
     * **/
    @Test
    @Transactional
    @Rollback
    public void testAddManagerAndAddHotelAndAddRoomAndAddCoupon(){
        addManager();
        addHotel();
        addRoom();
        addHotelTargetMoneyCoupon();
        addHotelBirthdayCoupon();
        addHotelMultipleRoomCoupon();
        addHotelTimeCoupon();
    }

    /**
     * add manager -> add hotel -> add room -> update hotel information
     * **/
    @Test
    @Transactional
    @Rollback
    public void testAddManagerAndAddHotelAndAddRoomAndUpdateHotelInfo(){
        addManager();
        addHotel();
        addRoom();
        updateHotelInfo();
    }

    /**
     * add manager -> add hotel -> add room -> add coupons -> register user -> login user
     * 1. user reserves hotel -> cancel order
     * 2. user reserves hotel -> manager check in -> manager check out
     * 3. user reserves hotel -> manager set error order -> manager remove error state -> manager check out
     * **/
    @Test
    @Transactional
    @Rollback
    public void testSystem(){
        addManager();
        addHotel();
        addRoom();
        addHotelTimeCoupon();
        addHotelMultipleRoomCoupon();
        addHotelBirthdayCoupon();
        addHotelTargetMoneyCoupon();
        register();
        login();

        //1.
        System.out.println("condition 1.");
        reserveHotel();
        cancelOrder();

        //2.
        System.out.println("condition 2.");
        reserveHotel();
        checkIn();
        checkOut();

        //3.
        System.out.println("condition 3.");
        reserveHotel();
        setErrorOrder();
        rmErrorOrder();
        checkOut();
    }

    private void register(){
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        assert accountController.registerAccount(userVO).getSuccess();
        List<User> list = (List<User>) adminController.getAllClients().getContent();
        user.setId(list.get(list.size() - 1).getId());
        System.out.println("Success register");
    }

    private void login(){
        UserForm userForm = new UserForm();
        userForm.setEmail(user.getEmail());
        userForm.setPassword(user.getPassword());
        ResponseVO result = accountController.login(userForm);
        assert result.getSuccess();
        User user1 = (User) result.getContent();
        Assert.assertEquals(user1.getId(), user.getId());
        Assert.assertEquals(user1.getEmail(), user.getEmail());
        Assert.assertEquals(user1.getPassword(), user.getPassword());
        Assert.assertEquals(user1.getUserName(), user.getUserName());
        System.out.println("Success login");
    }

    private void deleteUser(){
        User manager1 = (User) accountController.getUserInfo(manager.getId()).getContent();
        Assert.assertEquals(manager1.getId(), manager.getId());
        assert adminController.deleteUser(manager.getId()).getSuccess();
        ResponseVO res = accountController.getUserInfo(manager.getId());
        assert !res.getSuccess();
        Assert.assertNull(res.getContent());
        System.out.println("Success delete user");
    }

    private void rechargeCredit(){
        adminController.rechargeCredit(10,user.getId());
        User user1 = (User) accountController.getUserInfo(user.getId()).getContent();
        Assert.assertEquals(user1.getCredit(), 1100.0, 0.5);
        adminController.rechargeCredit(-20,user.getId());
        user1 = (User) accountController.getUserInfo(user.getId()).getContent();
        Assert.assertEquals(user1.getCredit(), -900.0, 0.5);
        System.out.println("Success recharge credit");
    }

    private void addRemark(){
        Remark remark = new Remark();
        remark.setHotelId(hotel.getId());
        remark.setContent("123");
        remark.setStar(3);
        remark.setUserId(manager.getId());
        remark.setRemarkTime((new SimpleDateFormat("yyyy-MM-dd")).format(new Date(System.currentTimeMillis())));
        remark.setUserName("123");
        try {
            assert hotelController.addRemark(remark).getSuccess();
        }catch (Exception e){
            System.out.println("Fail to add remark");
            assert false;
        }
        ResponseVO res = hotelController.retrieveHotelDetail(hotel.getId());
        assert res.getSuccess();
        HotelVO hotelVO = (HotelVO) res.getContent();
        List<RemarkVO> remarks = hotelVO.getRemarks();
        Assert.assertEquals(remarks.size() , 1);
        Assert.assertEquals(remarks.get(0).getHotelId(), remark.getHotelId());
        Assert.assertEquals(remarks.get(0).getContent(), remark.getContent());
        System.out.println("Success add remarks");
    }

    private void updateInfo(){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserName("456789");
        userInfoVO.setPassword("123456789");
        userInfoVO.setPhoneNumber("123");
        assert accountController.updateInfo(userInfoVO, user.getId()).getSuccess();
        ResponseVO res = accountController.getUserInfo(user.getId());
        assert res.getSuccess();
        User user1 = (User) res.getContent();
        Assert.assertEquals(userInfoVO.getUserName(), user1.getUserName());
        Assert.assertEquals(userInfoVO.getPassword(), user1.getPassword());
        Assert.assertEquals(userInfoVO.getPhoneNumber(), user1.getPhoneNumber());
        System.out.println("Success update user information");
    }

    private void reserveHotel(){
        order.setUserId(user.getId());
        order.setHotelId(hotel.getId());
        order.setRoomId(hotelRoom.getId());
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        Assert.assertTrue(orderController.reserveHotel(orderVO).getSuccess());
        System.out.println("Success reserve hotel");

        ResponseVO orderList = orderController.retrieveUserOrders(order.getUserId());
        assert orderList.getSuccess();
        List<Order> list = (List<Order>) orderList.getContent();
        //Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(list.size() - 1).getOrderState(), order.getOrderState());
        Assert.assertEquals(list.get(list.size() - 1).getUserId(), user.getId());
        Assert.assertEquals(list.get(list.size() - 1).getRoomId(), order.getRoomId());
        Assert.assertEquals(list.get(list.size() - 1).getCheckInDate(), order.getCheckInDate());
        Assert.assertEquals(list.get(list.size() - 1).getCheckOutDate(), order.getCheckOutDate());
        Assert.assertEquals(list.get(list.size() - 1).getRoomNum(), order.getRoomNum());
        Assert.assertEquals(list.get(list.size() - 1).isHaveChild(), order.isHaveChild());
        order.setId(list.get(list.size() - 1).getId());
        System.out.println("Success check order content");
    }

    private void checkIn(){
        assert orderController.orderCheckIn(order.getId()).getSuccess();
        ResponseVO orderList = orderController.retrieveUserOrders(order.getUserId());
        assert orderList.getSuccess();
        List<Order> list = (List<Order>) orderList.getContent();
        //Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(list.size() - 1).getOrderState(), OrderState.CheckIn.toString());
        System.out.println("Success check in");
    }

    private void checkOut(){
        assert ((Order)orderController.getOrderByOrderId(order.getId()).getContent()).getOrderState().equals(OrderState.CheckIn.toString());
        assert orderController.orderCheckOut(order.getId()).getSuccess();
        ResponseVO orderList = orderController.retrieveUserOrders(order.getUserId());
        assert orderList.getSuccess();
        List<Order> list = (List<Order>) orderList.getContent();
        //Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(list.size() - 1).getOrderState(), OrderState.CheckOut.toString());

        //test credit
        ResponseVO creditList = accountController.retrieveCreditRecord(user.getId());
        assert creditList.getSuccess();
        List<Credit> credits = (List<Credit>) creditList.getContent();
        credits.get(credits.size() - 1).setCreditChange(order.getPrice());
        System.out.println("Success check out");
    }

    private void cancelOrder(){
        assert orderController.annulOrder(order.getId()).getSuccess();
        ResponseVO orderList = orderController.retrieveUserOrders(order.getUserId());
        assert orderList.getSuccess();
        List<Order> list = (List<Order>) orderList.getContent();
        //Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(list.size() - 1).getOrderState(), OrderState.Cancel.toString());
        System.out.println("Success cancel order");
    }

    private void setErrorOrder(){
        assert orderController.errorOrder(order.getId()).getSuccess();
        ResponseVO orderList = orderController.retrieveUserOrders(order.getUserId());
        assert orderList.getSuccess();
        List<Order> list = (List<Order>) orderList.getContent();
        //Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(list.size() - 1).getOrderState(), OrderState.Error.toString());

        //test credit
        ResponseVO creditList = accountController.retrieveCreditRecord(user.getId());
        assert creditList.getSuccess();
        List<Credit> credits = (List<Credit>) creditList.getContent();
        credits.get(credits.size() - 1).setCreditChange(-order.getPrice());
        System.out.println("Success set error order");
    }

    private void rmErrorOrder(){
        assert ((Order)orderController.getOrderByOrderId(order.getId()).getContent()).getOrderState().equals(OrderState.Error.toString());
        assert orderController.rmErrorOrder(order.getId()).getSuccess();
        ResponseVO orderList = orderController.retrieveUserOrders(order.getUserId());
        assert orderList.getSuccess();
        List<Order> list = (List<Order>) orderList.getContent();
        //Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(list.size() - 1).getOrderState(), OrderState.CheckIn.toString());

        //test credit
        ResponseVO creditList = accountController.retrieveCreditRecord(user.getId());
        assert creditList.getSuccess();
        List<Credit> credits = (List<Credit>) creditList.getContent();
        credits.get(credits.size() - 1).setCreditChange(-order.getPrice());
        System.out.println("Success set error order");
    }

    private void addManager(){
        UserForm userForm = new UserForm();
        userForm.setEmail(manager.getEmail());
        userForm.setPassword(manager.getPassword());
        assert adminController.addManager(userForm).getSuccess();
        ResponseVO managersList = adminController.getAllManagers();
        assert managersList.getSuccess();
        List<User> list = (List<User>) managersList.getContent();
        Assert.assertEquals(list.get(list.size() - 1).getEmail(), manager.getEmail());
        Assert.assertEquals(list.get(list.size() - 1).getPassword(), manager.getPassword());
        manager.setId(list.get(list.size() - 1).getId());
        System.out.println("Success add manager");
    }

    private void addHotel(){
        hotel.setManagerId(manager.getId());
        HotelVO hotelVO = new HotelVO();
        hotelVO.setAddress(hotel.getAddress());
        hotelVO.setBizRegion("XiDan"); //not hotel.getBizRegion().toString();
        hotelVO.setDescription(hotel.getDescription());
        hotelVO.setName(hotel.getHotelName());
        hotelVO.setHotelStar("Five"); //not hotel.getHotelStar().toString()
        hotelVO.setManagerId(manager.getId());
        hotelVO.setPhoneNum(hotel.getPhoneNum());
        hotelVO.setRate(hotel.getRate());
        try {
            assert hotelController.createHotel(hotelVO).getSuccess();
        }catch (Exception e){
            System.out.println("Fail to create hotel");
            assert false;
        }
        ResponseVO res = hotelController.retrieveAllHotels();
        assert res.getSuccess();
        List<HotelVO> hotelList = (List<HotelVO>) res.getContent();
        Assert.assertEquals(hotelList.get(hotelList.size() - 1).getName(), hotel.getHotelName());
        Assert.assertEquals(hotelList.get(hotelList.size() - 1).getAddress(), hotel.getAddress());
        hotel.setId(hotelList.get(hotelList.size() - 1).getId());
        System.out.println("Success add hotel");
    }

    private void addRoom(){
        hotelRoom.setHotelId(hotel.getId());
        hotelController.addRoomInfo(hotelRoom);
        ResponseVO res = hotelController.retrieveHotelDetail(hotel.getId());
        assert res.getSuccess();
        HotelVO hotelVO = (HotelVO) res.getContent();
        RoomVO roomVO = hotelVO.getRooms().get(hotelVO.getRooms().size() - 1);
        Assert.assertEquals(roomVO.getCurNum(), Integer.valueOf(hotelRoom.getCurNum()));
        Assert.assertEquals(roomVO.getPrice(), hotelRoom.getPrice(), 0.5);
        Assert.assertEquals(roomVO.getTotal(), Integer.valueOf(hotelRoom.getTotal()));
        hotelRoom.setId(roomVO.getId());
        System.out.println("Success add room");
    }

    private void addHotelTargetMoneyCoupon(){
        HotelTargetMoneyCouponVO couponVO = new HotelTargetMoneyCouponVO();
        couponVO.setDiscountMoney(100);
        couponVO.setHotelId(hotel.getId());
        couponVO.setTargetMoney(1000);
        couponVO.setDescription("123");
        couponVO.setName("123");
        couponVO.setType(3);
        couponVO.setStatus(1);
        assert couponController.addHotelTargetMoneyCoupon(couponVO).getSuccess();
        //CouponVO couponVO = (CouponVO) result.getContent();
        ResponseVO couponsList = couponController.getHotelAllCoupons(hotel.getId());
        assert couponsList.getSuccess();
        List<Coupon> res = (List<Coupon>) couponsList.getContent();
        //List<Coupon> coupons = couponMapper.selectByHotelId(hotel.getId());
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
        System.out.println("Success add HotelTargetMoneyCoupon");
    }

    private void addHotelBirthdayCoupon(){
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
        ResponseVO couponsList = couponController.getHotelAllCoupons(hotel.getId());
        assert couponsList.getSuccess();
        List<Coupon> res = (List<Coupon>) couponsList.getContent();
        //List<Coupon> coupons = couponMapper.selectByHotelId(hotel.getId());
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
        System.out.println("Success add HotelBirthdayCoupon");
    }

    private void addHotelMultipleRoomCoupon(){
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
        ResponseVO couponsList = couponController.getHotelAllCoupons(hotel.getId());
        assert couponsList.getSuccess();
        List<Coupon> res = (List<Coupon>) couponsList.getContent();
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
        System.out.println("Success add HotelMultipleRoomCoupon");
    }

    private void addHotelTimeCoupon(){
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
        ResponseVO couponsList = couponController.getHotelAllCoupons(hotel.getId());
        assert couponsList.getSuccess();
        List<Coupon> res = (List<Coupon>) couponsList.getContent();
        Assert.assertNotNull(res);
        Assert.assertEquals(res.get(res.size() - 1).getStatus(), couponVO.getStatus());
        Assert.assertEquals(res.get(res.size() - 1).getCouponType(), couponVO.getType());
        Assert.assertEquals(res.get(res.size() - 1).getTargetMoney(), couponVO.getTargetMoney(), 0.5);
        Assert.assertEquals(res.get(res.size() - 1).getDiscountMoney(), couponVO.getDiscountMoney(), 0.5);
        System.out.println("Success add HotelTimeCoupon");
    }

    private void updateRoomCurNum(){
        assert hotelController.updateRoomCurNum(hotelRoom.getId(), 10).getSuccess();
        ResponseVO res = hotelController.retrieveHotelDetail(hotel.getId());
        assert res.getSuccess();
        HotelVO hotelVO = (HotelVO) res.getContent();
        RoomVO roomVO = hotelVO.getRooms().get(hotelVO.getRooms().size() - 1);
        Assert.assertEquals(roomVO.getCurNum(), Integer.valueOf(hotelRoom.getCurNum() - 10));
        hotelRoom.setCurNum(hotelRoom.getCurNum() - 10);
        System.out.println("Success update room current number");
    }

    private void updateRoomTotalNum(){
        assert hotelController.updateRoomTotalNum(hotelRoom.getId(), 10).getSuccess();
        ResponseVO res = hotelController.retrieveHotelDetail(hotel.getId());
        assert res.getSuccess();
        HotelVO hotelVO = (HotelVO) res.getContent();
        RoomVO roomVO = hotelVO.getRooms().get(hotelVO.getRooms().size() - 1);
        Assert.assertEquals(roomVO.getTotal(), Integer.valueOf(hotelRoom.getTotal() - 10));
        System.out.println("Success update room total number");
    }

    private void updateHotelInfo(){
        HotelInfoVO hotelInfoVO = new HotelInfoVO();
        hotelInfoVO.setName("456");
        hotelInfoVO.setAddress(hotel.getAddress());
        hotelInfoVO.setBizRegion(hotel.getBizRegion().toString());
        hotelInfoVO.setDescription(hotel.getDescription());
        hotelInfoVO.setHotelStar(hotel.getHotelStar().toString());
        hotelInfoVO.setPhoneNum(hotel.getPhoneNum());

        assert hotelController.updateHotelInfo(hotelInfoVO, hotel.getId()).getSuccess();
        ResponseVO res = hotelController.retrieveHotelDetail(hotel.getId());
        assert res.getSuccess();
        HotelVO hotelVO = (HotelVO) res.getContent();
        Assert.assertEquals(hotelVO.getName(), hotelInfoVO.getName());
        Assert.assertEquals(hotelVO.getAddress(), hotel.getAddress());
        System.out.println("Success update hotel information");
    }

}
