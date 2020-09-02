package com.example.hotel.UnitTest.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.remark.RemarkService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.controller.hotel.HotelController;
import com.example.hotel.controller.order.OrderController;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.remark.RemarkMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.enums.*;
import com.example.hotel.po.*;
import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerTest {
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
    @Autowired
    RoomService roomService;
    @Autowired
    HotelController hotelController;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    RemarkMapper remarkMapper;

    private Order order;
    private OrderVO orderVO;
    private User user;
    private User manager;
    private HotelVO hotel;
    private HotelRoom hotelRoom;

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
        //System.out.println(adminMapper.getAllManagers().size());

        //init a hotel
        hotel = new HotelVO();
        hotel.setAddress("123");
        hotel.setBizRegion("XiDan");
        hotel.setDescription("321");
        hotel.setName("1234");
        hotel.setHotelStar("Five");
        hotel.setManagerId(manager.getId());
        hotel.setPhoneNum("123");
        hotel.setRate(1.0);

        //init a room
        hotelRoom = new HotelRoom();
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setCurNum(100);
        hotelRoom.setPrice(100);
        hotelRoom.setTotal(100);

//        hotelMapper.insertHotel(hotel);
//        hotel.setId(hotelMapper.selectAllHotel().get(hotelMapper.selectAllHotel().size() - 1).getId());
//
//        //init a new user
//        user = new User();
//        user.setEmail("12345@qq.com");
//        user.setPassword("12345678");
//        user.setUserType(UserType.HotelManager);
//        user.setBirthday("2000-01-01");
//        user.setCredit(100);
//        user.setUserName("aaa");
//        user.setPhoneNumber("123");
//        User user1 = encryptionService.encryptionOfUser(user);
//        accountMapper.createNewAccount(user1);
//        user.setId(accountMapper.getAccountByEmail(user1.getEmail()).getId());
//        //System.out.println(user.getId());
//
//        //init a new order
//        order = new Order();
//        orderVO = new OrderVO();
//        order.setUserId(user.getId());
//        order.setHotelId(1);
//        order.setHotelName("汉庭酒店");
//        order.setCheckInDate((new SimpleDateFormat("yyyy-MM-dd")).format(new Date(System.currentTimeMillis())));
//        order.setCheckOutDate("2020-07-12");
//        order.setRoomType(RoomType.BigBed.toString());
//        order.setRoomId(2);
//        order.setRoomNum(1);
//        order.setPeopleNum(1);
//        order.setHaveChild(false);
//        order.setCreateDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis())));
//        order.setPrice(100.0);
//        order.setClientName("test");
//        order.setPhoneNumber("123");
//        order.setOrderState(OrderState.Reverse.toString());
//        BeanUtils.copyProperties(order,orderVO);            // 通过这个方法将orderVo的属性复制到order
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateHotel() throws Exception {
        assert hotelController.createHotel(hotel).getSuccess();
        List<HotelVO> hotelList = hotelMapper.selectAllHotel();
        Assert.assertEquals(hotelList.get(hotelList.size() - 1).getName(), hotel.getName());
        Assert.assertEquals(hotelList.get(hotelList.size() - 1).getAddress(), hotel.getAddress());
        hotel.setId(hotelList.get(hotelList.size() - 1).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveAllHotels() throws Exception {
        ResponseVO result = hotelController.retrieveAllHotels();
        assert result.getSuccess();
        List<HotelVO> res = (List) result.getContent();
        List<HotelVO> truth = (List) hotelMapper.selectAllHotel();
        for (int i = 0 ; i < res.size() ; i++){
            Assert.assertEquals(res.get(i).getId(), truth.get(i).getId());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveHotelsByUserId() throws Exception {
        testCreateHotel();
        ResponseVO result = hotelController.retrieveHotelsByUserId(manager.getId());
        assert result.getSuccess();
        List<HotelVO> list = (List) result.getContent();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getAddress(), hotel.getAddress());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddRoomInfo() throws Exception {
        testCreateHotel();
        hotelRoom.setHotelId(hotel.getId());
        assert hotelController.addRoomInfo(hotelRoom).getSuccess();
        List<HotelRoom> list = roomMapper.selectRoomsByHotelId(hotel.getId());
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getRoomType(), hotelRoom.getRoomType());
        hotelRoom.setId(list.get(0).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveHotelDetail() throws Exception {
        testCreateHotel();
        ResponseVO result = hotelController.retrieveHotelDetail(hotel.getId());
        assert result.getSuccess();
        HotelVO res = (HotelVO) result.getContent();
        Assert.assertEquals(hotel.getId(), res.getId());
        Assert.assertEquals(hotel.getAddress(), res.getAddress());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddRemark() throws Exception {
        testCreateHotel();
        Remark remark = new Remark();
        remark.setHotelId(hotel.getId());
        remark.setContent("123");
        remark.setStar(3);
        remark.setUserId(manager.getId());
        remark.setRemarkTime((new SimpleDateFormat("yyyy-MM-dd")).format(new Date(System.currentTimeMillis())));
        remark.setUserName("123");
        assert hotelController.addRemark(remark).getSuccess();
        List<Remark> list = remarkMapper.getRemarkByHotelId(hotel.getId());
        Assert.assertEquals(list.size() , 1);
        Assert.assertEquals(list.get(0).getHotelId(), remark.getHotelId());
        Assert.assertEquals(list.get(0).getContent(), remark.getContent());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateRoomCurNum() throws Exception {
        testAddRoomInfo();
        assert hotelController.updateRoomCurNum(hotelRoom.getId(), 10).getSuccess();
        Assert.assertEquals(roomMapper.getRoomById(hotelRoom.getId()).getCurNum(), hotelRoom.getCurNum() - 10);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateRoomTotalNum() throws Exception {
        testAddRoomInfo();
        assert hotelController.updateRoomTotalNum(hotelRoom.getId(), 10).getSuccess();
        Assert.assertEquals(roomMapper.getRoomById(hotelRoom.getId()).getTotal(), hotelRoom.getTotal() - 10);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteHotel() throws Exception {
        testCreateHotel();
        assert hotelController.deleteHotel(hotel.getId()).getSuccess();
        Assert.assertNull(hotelMapper.selectById(hotel.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateHotelInfo() throws Exception {
        testCreateHotel();
        HotelInfoVO hotelInfoVO = new HotelInfoVO();
        hotelInfoVO.setName("456");
        hotelInfoVO.setAddress(hotel.getAddress());
        hotelInfoVO.setBizRegion(hotel.getBizRegion());
        hotelInfoVO.setDescription(hotel.getDescription());
        hotelInfoVO.setHotelStar(hotel.getHotelStar());
        hotelInfoVO.setPhoneNum(hotel.getPhoneNum());
        assert hotelController.updateHotelInfo(hotelInfoVO, hotel.getId()).getSuccess();
        Assert.assertEquals(hotelMapper.selectById(hotel.getId()).getName(), hotelInfoVO.getName());
        Assert.assertEquals(hotelMapper.selectById(hotel.getId()).getAddress(), hotel.getAddress());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme