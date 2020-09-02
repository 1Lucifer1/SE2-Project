package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.OrderState;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.vo.HotelSearchVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final static String TOO_LITTLE="不能修改，会导致现存房间数为负数";

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        roomMapper.insertRoom(hotelRoom);
    }

    @Override
    public void updateRoomInfo(Integer roomId, Integer rooms) {
        roomMapper.updateRoomInfo(roomId,rooms);
    }

    @Override
    public int getRoomCurNum(Integer roomId) {
        return roomMapper.getRoomCurNum(roomId);
    }

    @Override
    public HotelRoom getRoomInfo(Integer roomId) {
        return roomMapper.getRoomById(roomId);
    }

    @Override
    public void deleteRoomsByHotelId(Integer hotelId) {
        roomMapper.deleteRoomsByHotelId(hotelId);
    }

    @Override
    public ResponseVO updateRoomTotalNum(Integer roomId, Integer totalChange) {
        System.out.println(roomId);
        System.out.println(totalChange);
        if(getRoomCurNum(roomId)-totalChange<0){
            return ResponseVO.buildFailure(TOO_LITTLE);
        }
        roomMapper.updateRoomTotalNum(roomId,totalChange);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<HotelRoom> searchRoomsByInfo(Integer hotelId, String roomType, Double maxPrice, Double minPrice,
                                             Integer roomNum, String checkInDate, String checkOutDate) {
        List<HotelRoom> rooms=retrieveHotelRoomInfo(hotelId);
        List<HotelRoom> target=new ArrayList<>();

        out:for(HotelRoom room:rooms){
            if(roomType!=null){
                if (!room.getRoomType().toString().equals(roomType)){
                    continue;
                }
            }
            if(maxPrice!=null){
                if(room.getPrice()>maxPrice){
                    continue;
                }
            }
            if (minPrice!=null){
                if (room.getPrice()<minPrice){
                    continue;
                }
            }
            if(roomNum==null){
                roomNum=1;
            }

            if(checkInDate!=null && checkOutDate!=null){
                int maxReservedNum=0;

                try {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    long time = sf.parse(checkInDate).getTime();
                    long end = sf.parse(checkOutDate).getTime();
                    long oneDay = 1000 * 60 * 60 * 24L;
                    int tempReservedNum;

                    List<Order> orders=orderService.getRoomOrders(room.getId());

                    for( ;time<end;time+=oneDay ){
                        tempReservedNum=0;
                        for(Order order:orders){
                            // 想想异常算不算占用房间
                            if(order.getOrderState().compareTo(OrderState.Cancel.toString())!=0 && order.getOrderState().compareTo(OrderState.CheckOut.toString())!=0
                                    && sf.parse(order.getCheckInDate()).getTime() <= time && time < sf.parse(order.getCheckOutDate()).getTime()){
                                tempReservedNum+=order.getRoomNum();
                            }
                        }

                        //每次都判断好像可以减小工作量
                        if(room.getTotal()-roomNum < tempReservedNum ){
                            continue out;
                        }

                        if(maxReservedNum<tempReservedNum){
                            maxReservedNum=tempReservedNum;
                        }
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("时间格式不对");
                }
            }


            target.add(room);
        }

        return target;
    }

    @Override
    public boolean hasRoomByInfo(Integer hotelId, String roomType, Double maxPrice, Double minPrice, Integer roomNum, String checkInDate, String checkOutDate) {
        List<HotelRoom> rooms=retrieveHotelRoomInfo(hotelId);

        out:for(HotelRoom room:rooms){
            if(roomType!=null){
                if (!room.getRoomType().toString().equals(roomType)){
                    continue;
                }
            }
            if(maxPrice!=null){
                if(room.getPrice()>maxPrice){
                    continue;
                }
            }
            if (minPrice!=null){
                if (room.getPrice()<minPrice){
                    continue;
                }
            }
            if(roomNum==null){
                roomNum=1;
            }

            if(checkInDate!=null && checkOutDate!=null){
                int maxReservedNum=0;

                try {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    long time = sf.parse(checkInDate).getTime();
                    long end = sf.parse(checkOutDate).getTime();
                    long oneDay = 1000 * 60 * 60 * 24L;
                    int tempReservedNum;

                    List<Order> orders=orderService.getRoomOrders(room.getId());

                    for( ;time<end;time+=oneDay ){
                        tempReservedNum=0;
                        for(Order order:orders){
                            // 想想异常算不算占用房间
                            if(order.getOrderState().compareTo(OrderState.Cancel.toString())!=0 && order.getOrderState().compareTo(OrderState.CheckOut.toString())!=0
                                    && sf.parse(order.getCheckInDate()).getTime() <= time && time < sf.parse(order.getCheckOutDate()).getTime()){
                                tempReservedNum+=order.getRoomNum();
                            }
                        }

                        //每次都判断好像可以减小工作量
                        if(room.getTotal()-roomNum < tempReservedNum ){
                            continue out;
                        }

                        if(maxReservedNum<tempReservedNum){
                            maxReservedNum=tempReservedNum;
                        }
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("时间格式不对");
                }
            }

            //存在房间满足以上所有条件
            return true;
        }

        // 不存在房间满足以上所有条件
        return false;
    }
}
