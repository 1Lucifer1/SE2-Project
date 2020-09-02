package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.remark.RemarkService;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Remark;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Documented;
import java.util.Map;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RemarkService remarkService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    //personal
    @GetMapping("/{managerId}/getManagerHotel")
    public ResponseVO retrieveHotelsByUserId(@PathVariable Integer managerId){
        //System.out.println(managerId);
        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByManagerId(managerId));
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @PostMapping("/addRemark")
    public ResponseVO addRemark(@RequestBody Remark remark) throws ServiceException {
        remarkService.addHotelRemark(remark);
        return ResponseVO.buildSuccess(true);
    }

//    //personal

    /**
     * 更改房间的现存房间数量（默认是减少）
     * @param roomId
     * @param curNum
     * @return
     */
    @PostMapping("/{roomId}/{curNum}/dealRoom") ///{roomId}/{num}/changeRoom
    public ResponseVO updateRoomCurNum(@PathVariable Integer roomId,@PathVariable Integer curNum){
        //System.out.println(num);
        //System.out.println(roomId);
        //z9System.out.println("进入后端了");
        if(-curNum+roomService.getRoomCurNum(roomId)>roomService.getRoomInfo(roomId).getTotal()){
            return ResponseVO.buildFailure("不能修改，会导致现存数量超过实际房间总数！");
        }
        if(roomService.getRoomCurNum(roomId)-curNum<0){
            return ResponseVO.buildFailure("不能修改，会导致现存房间数为负数");
        }
        hotelService.updateRoomInfo(roomId,curNum);
        return ResponseVO.buildSuccess(true);
    }


    /**
     * 更改房间的总数量（默认是减少）
     * @param roomId
     * @param total
     * @return
     */
    @PostMapping("/{roomId}/{total}/changeRoom")
    public ResponseVO updateRoomTotalNum(@PathVariable Integer roomId,@PathVariable Integer total){
        return roomService.updateRoomTotalNum(roomId,total);
    }


    @PostMapping("/{hotelId}/cancelHotel")
    public ResponseVO deleteHotel(@PathVariable Integer hotelId){
        return hotelService.deleteHotel(hotelId);
    }


    @PostMapping("/{id}/hotelInfo/updateHotelInfo")
    public ResponseVO updateHotelInfo(@RequestBody HotelInfoVO hotelInfoVO,@PathVariable Integer id){
        System.out.println("进入后端了");
        return hotelService.updateHotelInfo(hotelInfoVO,id);
    }

    @PostMapping("/{hotelId}/rateUpdate")
    public ResponseVO updateHotelRate(@PathVariable Integer hotelId, @RequestBody Map<String,String> data){
        String rate = data.get("rate");
        return hotelService.updateHotelRate(hotelId, rate);
    }

    @PostMapping("/search")
    public ResponseVO retrieveHotelByInfo(@RequestBody HotelSearchVO hotelSearchVO){
        return ResponseVO.buildSuccess(hotelService.retrieveHotelByInfo(hotelSearchVO));
    }

//    @GetMapping("/{userId}/getUserHotels")
//    public ResponseVO retrieveHotelOrdered(@PathVariable Integer userId){
//        System.out.println("进入后端了");
//        System.out.println(userId);
//        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByClientOrdered(userId));
//    }
//    @GetMapping("")
//    public ResponseVO retrieveHotelOrdered(@PathVariable Integer userId){
//        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByClientOrdered(userId));
//    }

}
