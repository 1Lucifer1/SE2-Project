package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CreditService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.CreditAction;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Credit;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String ACCOUNT_NULL = "账号不存在";
    private final static String EMAIL_EXIST = "该邮箱已注册";

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    CreditService creditService;
    @Autowired
    HotelService hotelService;
    @Autowired
    EncryptionService encryptionService;

    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.HotelManager);

        //加密 有null
        user=encryptionService.encryptionOfUser(user);

        if(accountService.getAccountByEmail(user.getEmail())!=null){
            return ResponseVO.buildFailure(EMAIL_EXIST);
        }

        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        List<User> users=adminMapper.getAllManagers();

        // 解密
        for(int i=0;i<users.size();i++){
            users.set(i,encryptionService.decryptionOfUser(users.get(i)));
        }
        return users;
    }

    @Override
    public List<User> getAllClients() {
        List<User> users=adminMapper.getAllClients();

        // 解密
        for(int i=0;i<users.size();i++){
            users.set(i,encryptionService.decryptionOfUser(users.get(i)));
        }
        return users;
    }

    @Override
    public ResponseVO deleteUser(int userId) {
        try{
            //得到的时候已经解密了
            User user=accountService.getUserInfo(userId);

            if(user.getUserType().equals(UserType.HotelManager)){
                //  ********************或者可以在hotelmapper里写一个方法直接删除所有manager_id==userId的酒店的方法***************
                List<HotelVO> hotels=hotelService.retrieveHotelsByManagerId(userId);
                for(HotelVO hotel:hotels){
                    hotelService.deleteHotel(hotel.getId());
                }
            }
            adminMapper.deleteUser(userId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_NULL);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO rechargeCredit(int rechargePoint, int userId) {
        try{
            accountService.updateCredit(userId,-rechargePoint*100);

            //得到的时候已经解密了
            User user=accountService.getUserInfo(userId);

            // ************需要在信用系统中留下记录！！！！！！！！！！！！！！！！！！！！******************************
            Credit credit=new Credit();
            credit.setActionType(CreditAction.Invest.toString());
            credit.setCreditChange(rechargePoint*100);
            credit.setCreditResult(user.getCredit());
            credit.setOrderId(null);
            credit.setUserId(userId);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            credit.setChangeTime(sf.format(curDate));

            creditService.addCreditRecord(credit);

            return ResponseVO.buildSuccess(true);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("未知异常，请联系官方");
        }
    }

}
