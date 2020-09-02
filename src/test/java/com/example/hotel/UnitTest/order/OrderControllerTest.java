package com.example.hotel.UnitTest.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.controller.order.OrderController;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.enums.*;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
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
public class OrderControllerTest {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CreditMapper creditMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderController orderController;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    HotelMapper hotelMapper;
    @Autowired
    EncryptionService encryptionService;

    private Order order;
    private OrderVO orderVO;
    private User user;
    private User manager;
    private Hotel hotel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        //init a manager
        manager = new User();
        manager.setEmail("1234@qq.com");
        manager.setPassword("12345678");
        manager.setUserType(UserType.HotelManager);
        User user0 = encryptionService.encryptionOfUser(manager);
        adminMapper.addManager(user0);
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


        //init a new user
        user = new User();
        user.setEmail("12345@qq.com");
        user.setPassword("12345678");
        user.setUserType(UserType.HotelManager);
        user.setBirthday("2000-01-01");
        user.setCredit(100);
        user.setUserName("aaa");
        user.setPhoneNumber("123");
        User user1 = encryptionService.encryptionOfUser(user);
        accountMapper.createNewAccount(user1);
        user.setId(accountMapper.getAccountByEmail(user1.getEmail()).getId());
        //System.out.println(user.getId());

        //init a new order
        order = new Order();
        orderVO = new OrderVO();
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
        BeanUtils.copyProperties(order,orderVO);            // 通过这个方法将orderVo的属性复制到order

    }


    @Test
    @Transactional
    @Rollback
    public void testReserveHotel() throws Exception {
        //when(orderService.addOrder(any())).thenReturn(new ResponseVO());
        assert (orderController.reserveHotel(orderVO).getSuccess());
        //when().thenReturn(ResponseVO.buildSuccess());

        Assert.assertEquals(orderMapper.getRoomOrders(order.getRoomId()).get(orderMapper.getRoomOrders(order.getRoomId()).size() - 1).getId(),
                orderMapper.getUserOrders(order.getUserId()).get(orderMapper.getUserOrders(order.getUserId()).size() - 1).getId());
        //when(orderMapper.getRoomOrders(0)).thenReturn(Arrays.<Order>asList(order));
        //when(orderMapper.getUserOrders(0)).thenReturn(Arrays.<Order>asList(order));
        //ResponseVO result = orderController.reserveHotel(new OrderVO());
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveAllOrders() throws Exception {
        //when(orderService.getAllOrders()).thenReturn(Arrays.<Order>asList(new Order()));
        ResponseVO result = orderController.retrieveAllOrders();
        assert (result.getSuccess());
        List<Order> truth = orderMapper.getAllOrders();
        List<Order> res = (List) result.getContent();
        for(int i = 0 ; i <  res.size() ; i++){
            if(truth.get(i).getId() != res.get(i).getId() ) assert(false);
        }
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveManageOrders() throws Exception {
        ResponseVO result = orderController.retrieveManageOrders(manager.getId());
        assert (result.getSuccess());
        List<HotelVO> hotels = hotelService.retrieveHotelsByManagerId(manager.getId());
        List<Order> res = (List) result.getContent();
        for(int i = 0 ; i < res.size() ; i++){
            boolean test = false;
            for(int j = 0 ; j < hotels.size() ; j++){
                if(res.get(i).getHotelId().equals(hotels.get(j).getId())) test = true;
            }
            assert (test);
        }
        //when(orderService.getManageOrders(anyInt())).thenReturn(Arrays.<Order>asList(new Order()));

        //ResponseVO result = orderController.retrieveManageOrders(0);
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveUserOrders() throws Exception {
        ResponseVO result = orderController.retrieveUserOrders(user.getId());
        assert (result.getSuccess());
        List<Order> res = (List) result.getContent();
        for(int i = 0 ; i < res.size() ; i++){
            if(res.get(i).getUserId() != user.getId()) assert(false);
        }
        //when(orderService.getUserOrders(anyInt())).thenReturn(Arrays.<Order>asList(new Order()));

        //ResponseVO result = orderController.retrieveUserOrders(0);
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testAnnulOrder() throws Exception {
        testReserveHotel();
        int orderId = orderMapper.getRoomOrders(order.getRoomId()).get(orderMapper.getRoomOrders(order.getRoomId()).size() - 1).getId();
        Assert.assertEquals(orderController.annulOrder(orderId).getSuccess(), true);
        Assert.assertEquals(orderMapper.getOrderById(orderId).getOrderState(), OrderState.Cancel.toString());
        //when(orderService.annulOrder(anyInt())).thenReturn(new ResponseVO());

        //ResponseVO result = orderController.annulOrder(0);
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveHotelOrders() throws Exception {
        ResponseVO result = orderController.retrieveHotelOrders(hotel.getId());
        assert (result.getSuccess());
        List<Order> res = (List) result.getContent();
        for(int i = 0 ; i < res.size() ; i++){
            if(res.get(i).getUserId() != hotel.getId()) assert(false);
        }
        //when(orderService.getHotelOrders(anyInt())).thenReturn(Arrays.<Order>asList(new Order()));
        //ResponseVO result = orderController.retrieveHotelOrders(Integer.valueOf(0));
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testOrderCheckIn() throws Exception {
        testReserveHotel();
        int orderId = orderMapper.getRoomOrders(order.getRoomId()).get(orderMapper.getRoomOrders(order.getRoomId()).size() - 1).getId();
        ResponseVO result = orderController.orderCheckIn(orderId);
        assert (result.getSuccess());
        Assert.assertEquals(orderMapper.getOrderById(orderId).getOrderState(), OrderState.CheckIn.toString());
        //when(orderService.orderCheckIn(anyInt())).thenReturn(new ResponseVO());

        //ResponseVO result = orderController.orderCheckIn(Integer.valueOf(0));
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testOrderCheckOut() throws Exception {
        testOrderCheckIn();
        int orderId = orderMapper.getRoomOrders(order.getRoomId()).get(orderMapper.getRoomOrders(order.getRoomId()).size() - 1).getId();
        ResponseVO result = orderController.orderCheckOut(orderId);
        assert (result.getSuccess());
        Assert.assertEquals(orderMapper.getOrderById(orderId).getOrderState(), OrderState.CheckOut.toString());
        //when(orderService.orderCheckOut(anyInt())).thenReturn(new ResponseVO());

        //ResponseVO result = orderController.orderCheckOut(Integer.valueOf(0));
        //Assert.assertEquals(new ResponseVO(), result);
    }

    private int addErrorOrder() throws Exception {
        orderVO.setCheckInDate((new SimpleDateFormat("yyyy-MM-dd")).format(
                (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(
                        orderVO.getCheckInDate().substring(0,8) + String.valueOf(Integer.parseInt(orderVO.getCheckInDate().substring(8, 10)) - 1) + " 12:00:00")));
        assert (orderController.reserveHotel(orderVO).getSuccess());
        return orderMapper.getRoomOrders(order.getRoomId()).get(orderMapper.getRoomOrders(order.getRoomId()).size() - 1).getId();
    }

    @Test
    @Transactional
    @Rollback
    public void testErrorOrder() throws Exception {
        int orderId = addErrorOrder();
        ResponseVO result = orderController.errorOrder(orderId);
        assert (result.getSuccess());
        Assert.assertEquals(orderMapper.getOrderById(orderId).getOrderState(), OrderState.Error.toString());

        //when(orderService.errorOrder(anyInt())).thenReturn(new ResponseVO());

        //ResponseVO result = orderController.errorOrder(Integer.valueOf(0));
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testRmErrorOrder() throws Exception {
        int orderId = addErrorOrder();
        assert (orderController.errorOrder(orderId).getSuccess());
        ResponseVO result = orderController.rmErrorOrder(orderId);
        assert (result.getSuccess());
        Assert.assertEquals(orderMapper.getOrderById(orderId).getOrderState(), OrderState.CheckIn.toString());
        //when(orderService.rmErrorOrder(anyInt())).thenReturn(new ResponseVO());

        //ResponseVO result = orderController.rmErrorOrder(Integer.valueOf(0));
        //Assert.assertEquals(new ResponseVO(), result);
    }

    @Test
    @Transactional
    @Rollback
    public void testGetOrderByOrderId() throws Exception {
        assert (orderController.reserveHotel(orderVO).getSuccess());
        Integer orderId = orderMapper.getRoomOrders(order.getRoomId()).get(orderMapper.getRoomOrders(order.getRoomId()).size() - 1).getId();
        ResponseVO result = orderController.getOrderByOrderId(orderId);
        assert  (result.getSuccess());
        Order res = (Order)result.getContent();
        Assert.assertEquals(orderId, res.getId());
        //when(orderService.getOrderByOrderId(anyInt())).thenReturn(new ResponseVO());

        //ResponseVO result = orderController.getOrderByOrderId(0);
        //Assert.assertEquals(new ResponseVO(), result);
    }

}
