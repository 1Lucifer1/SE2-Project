package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CreditService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.CreditAction;
import com.example.hotel.enums.OrderState;
import com.example.hotel.po.Credit;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String CREDIT_LACK = "信用值太低";
    private final static String ANNUL_ERROR = "撤销失败";
    private final static String WRONG_TIME_IN = "不在预定时间之内，入住失败";
    private final static String WRONG_TIME_ERROR = "未到预定入住时间，操作失败";
    private final static String WRONG_STATE_IN = "该订单状态下不能入住";
    private final static String WRONG_STATE_OUT = "非已入住状态下不能退房";
    private final static String WRONG_STATE_DELAY = "非异常订单状态下不能延迟入住";
    private final static String ANNUL_ALREADY="订单已撤销，撤销失败";
    private final static String IN_ALREADY="已入住，入住失败";
    private final static String OUT_ALREADY="已退房，退房失败";


    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    CreditService creditService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();

        //修改了对于当前预定时间下房间是否有剩余的判定        需要验证逻辑写的对不对
        HotelRoom room=hotelService.getRoomInfo(orderVO.getRoomId());
        int maxReservedNum=0;

        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Long time = sf.parse(orderVO.getCheckInDate()).getTime();
            Long end = sf.parse(orderVO.getCheckOutDate()).getTime();
            Long oneDay = 1000 * 60 * 60 * 24L;
            int tempReservedNum;

            List<Order> orders=getRoomOrders(orderVO.getRoomId());

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
                if(room.getTotal()-reserveRoomNum < tempReservedNum ){
                    return ResponseVO.buildFailure(ROOMNUM_LACK);
                }

                if(maxReservedNum<tempReservedNum){
                    maxReservedNum=tempReservedNum;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("新增订单异常，请联系官方处理");
        }

        //每次都判断好像可以减小工作量
        if(room.getTotal()-reserveRoomNum < maxReservedNum ){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }


        try {
            // 转移了位置
            User user = accountService.getUserInfo(orderVO.getUserId());
            if(user.getCredit()<0){
                return ResponseVO.buildFailure(CREDIT_LACK);
            }

            //修改时间为 yyyy-MM-dd HH:mm:ss
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);            // 通过这个方法将orderVo的属性复制到order
            orderMapper.addOrder(order);
            //hotelService.updateRoomInfo(orderVO.getRoomId(),orderVO.getRoomNum());       //转移到 checkIn 的时候改变curNum
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getManageOrders(int userId) {
        List<Order> orders=new ArrayList<>();
        List<HotelVO> hotels = hotelService.retrieveHotelsByManagerId(userId);
        for(HotelVO hotel:hotels){
            orders.addAll(orderMapper.getHotelOrders(hotel.getId()));
        }
        return orders;
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        return orderMapper.getUserOrders(userId);
    }

    //取消订单逻辑的具体实现
    @Override
    public ResponseVO annulOrder(int orderId) {
        Order order = orderMapper.getOrderById(orderId);

        //新增对于订单状态的判断
        if (order.getOrderState().compareTo(OrderState.Cancel.toString())==0) {
            return ResponseVO.buildFailure(ANNUL_ALREADY);
        }

        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curdate = new Date(System.currentTimeMillis());
            String cancellationDate = sf.format(curdate);

            orderMapper.annulOrder(orderId, cancellationDate);        //对order状态的修改

            //hotelService.updateRoomInfo(order.getHotelId(), order.getRoomId(), -order.getRoomNum());        //不需要在这里恢复房间数

            // 由于原来的orderlist数据库没有cancellationDate属性，所以在hotel.sql里自行添加了cancellationDate属性，需要确认能run

            String inDate = order.getCheckInDate() + " 12:00:00";
            if ( sf.parse(inDate).getTime() - curdate.getTime() < 21600000) {       // 6小时
                accountService.updateCredit(order.getUserId(), order.getPrice() * 0.5);    // try
                User user=accountService.getUserInfo(order.getUserId());

                // ************需要在信用系统中留下记录！！！！！！！！！！！！！！！！！！！！******************************
                Credit credit=new Credit();
                credit.setActionType(CreditAction.Cancel.toString());
                credit.setCreditChange(-order.getPrice() * 0.5);
                credit.setCreditResult(user.getCredit());
                credit.setOrderId(orderId);
                credit.setUserId(order.getUserId());
                credit.setChangeTime(sf.format(curdate)); //感觉怪怪的 好像时间不一样（不同步

                creditService.addCreditRecord(credit);
                //
            }

            return ResponseVO.buildSuccess(true);
        }
        catch (Exception e){            // 这种情况出现感觉算是异常订单了，因为修改了数据但是却返回失败？？？？？？？？？？？？？？可以试着还原数据?或者把订单置为异常
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
    }

    //change

    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        //这是个什么玩意儿
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public List<Order> getRoomOrders(Integer roomId) {
        //List<Order> orders = getAllOrders();
        //return orders.stream().filter(order -> order.getRoomId().equals(roomId)).collect(Collectors.toList());
        return orderMapper.getRoomOrders(roomId);
    }

    @Override
    public ResponseVO orderCheckIn(Integer orderId) {
        Order order = orderMapper.getOrderById(orderId);

        //增加对当前订单状态的判断
        if (order.getOrderState().compareTo(OrderState.CheckIn.toString())==0) {
            return ResponseVO.buildFailure(IN_ALREADY);
        }
        if (order.getOrderState().compareTo(OrderState.Reverse.toString())!=0) {
            return ResponseVO.buildFailure(WRONG_STATE_IN);
        }

        return checkIn(orderId);

    }


    // 实际的入住方法，用来在入住和延迟入住下复用
    private ResponseVO checkIn(Integer orderId){

        try {
            Order order = orderMapper.getOrderById(orderId);

            //增加对当前是否在入住时间之内的判定
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            if(curDate.getTime()<sf.parse(order.getCheckInDate()).getTime() ||
                    curDate.getTime()>sf.parse(order.getCheckOutDate()).getTime() + 1000 * 60 * 60 * 12L ){
                return ResponseVO.buildFailure(WRONG_TIME_IN);
            }

            //增加对当前房间数量是否足够的判定
            if(hotelService.getRoomCurNum(order.getRoomId())-order.getRoomNum()< 0){
                return ResponseVO.buildFailure("房间数量不足，入住失败");
            }
            hotelService.updateRoomInfo(order.getRoomId(),order.getRoomNum());       //更新房间占用情况


            orderMapper.updateState(orderId, OrderState.CheckIn.toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(curDate);
            orderMapper.updateCheckInTime(orderId, date);

            accountService.updateCredit(order.getUserId(), -order.getPrice());    // -的是因为与mapper中的-抵消，负负得正
            User user=accountService.getUserInfo(order.getUserId());

            // ************需要在信用系统中留下记录！！！！！！！！！！！！！！！！！！！！******************************
            Credit credit=new Credit();
            credit.setActionType(CreditAction.Execute.toString());
            credit.setCreditChange(order.getPrice());
            credit.setCreditResult(user.getCredit());
            credit.setOrderId(orderId);
            credit.setUserId(order.getUserId());
            //credit.setChangeTime(sf.format(curDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())); //感觉怪怪的 好像时间不一样（不同步
            credit.setChangeTime(date);
            creditService.addCreditRecord(credit);
            //

            return ResponseVO.buildSuccess(date);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("入住失败");
        }

    }

    @Override
    public ResponseVO orderCheckOut(Integer orderId) {
        try{
            Order order = orderMapper.getOrderById(orderId);

            //增加对订单状态的判断
            if (order.getOrderState().compareTo(OrderState.CheckOut.toString())==0) {
                return ResponseVO.buildFailure(OUT_ALREADY);
            }
            if(order.getOrderState().compareTo(OrderState.CheckIn.toString())!=0){
                return ResponseVO.buildFailure(WRONG_STATE_OUT);
            }

            orderMapper.updateState(orderId, OrderState.CheckOut.toString());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String date = sf.format(curDate);
            orderMapper.updateCheckOutTime(orderId, date);

            hotelService.updateRoomInfo(order.getRoomId(), -order.getRoomNum());        //恢复占用的房间数

            return ResponseVO.buildSuccess(date);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("退房失败");
        }
    }

    @Override
    public ResponseVO errorOrder(Integer orderId) {
        try{
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String errorDate = sf.format(curDate);

            Order order = orderMapper.getOrderById(orderId);
            //直接修改为统一在checkIn的时候对curNum操作
            //hotelService.updateRoomInfo(order.getRoomId(),-order.getRoomNum());

            //增加时间限制，在预订入住当天的12点前，不能设置订单为异常订单 (感觉 时间可以再做商量)
            if(sf.parse(order.getCheckInDate()+ " 12:00:00" ).getTime() < sf.parse(errorDate).getTime()){
                orderMapper.updateState(orderId, OrderState.Error.toString());

                //扣除信用值
                accountService.updateCredit(order.getUserId(), order.getPrice());
                User user=accountService.getUserInfo(order.getUserId());

                //信用记录
                Credit credit=new Credit();
                credit.setActionType(CreditAction.Abnormal.toString());
                credit.setCreditChange(-order.getPrice());
                credit.setCreditResult(user.getCredit());
                credit.setOrderId(orderId);
                credit.setUserId(order.getUserId());
                //credit.setChangeTime(sf.format(curDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())); //感觉怪怪的 好像时间不一样（不同步
                credit.setChangeTime(sf.format(curDate));
                creditService.addCreditRecord(credit);
                orderMapper.updateErrorTime(orderId, errorDate);

                return ResponseVO.buildSuccess(errorDate);
            }
            else {
                return ResponseVO.buildFailure(WRONG_TIME_ERROR);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("未知异常");
        }
    }

    @Override
    public void timingErrorOrder() {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String errorDate = sf.format(curDate);

            List<Order> orders = orderMapper.getAllOrders();
            for (Order order : orders) {
                if (order.getOrderState().equals(OrderState.Reverse.toString())){
                    if(sf.parse(order.getCheckInDate()+ " 23:59:59" ).getTime() < curDate.getTime()) {
                        orderMapper.updateState(order.getId(), OrderState.Error.toString());

                        //扣除信用值
                        accountService.updateCredit(order.getUserId(), order.getPrice());
                        User user = accountService.getUserInfo(order.getUserId());

                        //信用记录
                        Credit credit = new Credit();
                        credit.setActionType(CreditAction.Abnormal.toString());
                        credit.setCreditChange(-order.getPrice());
                        credit.setCreditResult(user.getCredit());
                        credit.setOrderId(order.getId());
                        credit.setUserId(order.getUserId());
                        //credit.setChangeTime(sf.format(curDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())); //感觉怪怪的 好像时间不一样（不同步
                        credit.setChangeTime(sf.format(curDate));
                        creditService.addCreditRecord(credit);
                        orderMapper.updateErrorTime(order.getId(), errorDate);
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ResponseVO rmErrorOrder(Integer orderId) {
        try{
            Order order = orderMapper.getOrderById(orderId);

            //增加对订单状态的判断
            if (order.getOrderState().compareTo(OrderState.CheckIn.toString())==0) {
                return ResponseVO.buildFailure(IN_ALREADY);
            }
            if(order.getOrderState().compareTo(OrderState.Error.toString())!=0){
                return ResponseVO.buildFailure(WRONG_STATE_DELAY);
            }

            //增加对订单时间的判断
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            if(curDate.getTime()<sf.parse(order.getCheckInDate()).getTime() ||
                    curDate.getTime()>sf.parse(order.getCheckOutDate()).getTime() + 1000 * 60 * 60 * 12L ){
                return ResponseVO.buildFailure(WRONG_TIME_IN);
            }


            creditService.rmCreditRecord(orderId);

            //恢复信用值
            accountService.updateCredit(order.getUserId(), -order.getPrice());

            //直接修改为统一在checkIn的时候对curNum操作，还需要另外 决定异常会不会影响预订的位置！！！！！！！！！！！！！！！！！！！！！！！！！！
            //hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),order.getRoomNum());

            return checkIn(orderId);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("延迟入住异常，请联系官方");
        }
    }

    @Override
    public ResponseVO getOrderByOrderId(Integer orderId) {
        return ResponseVO.buildSuccess(orderMapper.getOrderById(orderId));
    }


    @Override
    public List<Integer> getAllOrderedHotelId(Integer userId) {
        return orderMapper.getAllOrderedHotelId(userId);
    }

    @Override
    public List<Integer> getNormalOrderedHotelId(Integer userId) {
        return orderMapper.getNormalOrderedHotelId(userId);
    }

    @Override
    public List<Integer> getErrorOrderedHotelId(Integer userId) {
        return orderMapper.getErrorOrderedHotelId(userId);
    }

    @Override
    public List<Integer> getCancelOrderedHotelId(Integer userId) {
        return orderMapper.getCancelOrderedHotelId(userId);
    }
}
