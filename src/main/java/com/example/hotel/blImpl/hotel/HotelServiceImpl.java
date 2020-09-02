package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.remark.RemarkService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.*;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final static String UPDATE_ERROR = "修改失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RemarkService remarkService;



    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(5.0);
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void updateRoomInfo(Integer roomId, Integer rooms) {
        roomService.updateRoomInfo(roomId,rooms);
    }

    @Override
    public int getRoomCurNum(Integer roomId) {
        return roomService.getRoomCurNum(roomId);
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        return hotelMapper.selectAllHotel();
    }

    //personal
    @Override
    public List<HotelVO> retrieveHotelsByManagerId(Integer managerId){
        return hotelMapper.selectManagerHotel(managerId);
    }

    @Override
    public List<OrderedHotelVO> retrieveHotelsByClientOrdered(Integer userId) {
        //目前想到两种方法：
        //1. 先得到所有的酒店，然后依次检查每一个酒店
        //2. 先的到所有被预定过的酒店的Id，然后依次检查每一个酒店
        List<Integer> hotelIds=orderService.getAllOrderedHotelId(userId);

        List<Integer> normal=orderService.getNormalOrderedHotelId(userId);
        List<Integer> error=orderService.getErrorOrderedHotelId(userId);
        List<Integer> cancel=orderService.getCancelOrderedHotelId(userId);

        List<OrderedHotelVO> target=new ArrayList<>();

        for(Integer id:hotelIds){
            //写一个单独得hotel的方法
            HotelVO hotel=hotelMapper.selectById(id);

            //酒店不存在
            if(hotel==null){
                continue;
            }

            target.add(new OrderedHotelVO(hotel,normal.contains(id),error.contains(id),cancel.contains(id)));
        }

        return target;
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        List<Remark> remarks = remarkService.retrieveHotelRemark(hotelId);

        List<RemarkVO> remarkVOS = remarks.stream().map(r -> {
            RemarkVO remarkVO = new RemarkVO();
            remarkVO.setId(r.getId());
            remarkVO.setContent(r.getContent());
            remarkVO.setUserId(r.getUserId());
            remarkVO.setHotelId(r.getHotelId());
            remarkVO.setStar(r.getStar());
            remarkVO.setRemarkTime(r.getRemarkTime());
            remarkVO.setUserName(r.getUserName());
            return remarkVO;
        }).collect(Collectors.toList());
        hotelVO.setRemarks(remarkVOS);
        return hotelVO;
    }


    //personal

    @Override
    public HotelRoom getRoomInfo(Integer roomId) {
        return roomService.getRoomInfo(roomId);
    }

    @Override
    public ResponseVO deleteHotel(Integer hotelId) {  //可能会影响其他东西，注意！！！！！！！！！！！！！！！
        try{
            roomService.deleteRoomsByHotelId(hotelId);
            hotelMapper.deleteHotel(hotelId);
            return ResponseVO.buildSuccess(true);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("删除失败");
        }
    }


    @Override
    public ResponseVO updateHotelInfo(HotelInfoVO hotelInfoVO, Integer id) {
        try {
            System.out.println(hotelInfoVO.getHotelStar());
            hotelMapper.updateHotelInfo(id,hotelInfoVO.getName(),hotelInfoVO.getAddress(),hotelInfoVO.getBizRegion(),
                    hotelInfoVO.getHotelStar(),hotelInfoVO.getDescription(),hotelInfoVO.getPhoneNum());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateHotelRate(Integer id, String rate) {
        hotelMapper.updateHotelRate(new Double(rate), id);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<HotelVO> retrieveHotelByInfo(HotelSearchVO hotelInfo) {
        List<HotelVO> hotels=retrieveHotels();
        List<HotelVO> target=new ArrayList<>();

        for(HotelVO hotel:hotels){
            if(hotelInfo.getName()!=null){
                if(hotel.getName()==null){
                    continue;
                }
                else{
                    if(!hotel.getName().contains(hotelInfo.getName())){
                        continue;
                    }
                }
            }
            if(hotelInfo.getAddress()!=null){
                if(hotel.getAddress()==null){
                    continue;
                }
                else{
                    if(!hotel.getAddress().contains(hotelInfo.getAddress())){
                        continue;
                    }
                }
            }
            if(hotelInfo.getBizRegion()!=null){
                if(hotel.getBizRegion()==null){
                    continue;
                }
                else{
                    if(!hotel.getBizRegion().contains(hotelInfo.getBizRegion())){
                        continue;
                    }
                }
            }
            if(hotelInfo.getHotelStar()!=null){
                if(hotel.getHotelStar()==null){
                    continue;
                }
                else{
                    if(!hotel.getHotelStar().equals(hotelInfo.getHotelStar())){
                        continue;
                    }
                }
            }

//            /*对房间属性进行判断*/
//            if(!roomService.hasRoomByInfo(hotel.getId(),hotelInfo.getRoomType(),hotelInfo.getMaxPrice(),
//                    hotelInfo.getMinPrice(),hotelInfo.getRoomNum(),
//                    hotelInfo.getCheckInDate(),hotelInfo.getCheckOutDate())){
//                continue;
//            }

            if(hotelInfo.getMaxRate()!=null){
                if(hotel.getRate()>hotelInfo.getMaxRate()){
                    continue;
                }
            }

            if(hotelInfo.getMinPrice()!=null){
                if(hotel.getRate()<hotelInfo.getMinPrice()){
                    continue;
                }
            }

            target.add(hotel);
        }


        return target;
    }

    @Override
    public HotelVO retrieveHotelById(Integer hotelId) {
        return hotelMapper.selectById(hotelId);
    }
}
