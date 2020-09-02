package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CreditService;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserInfoVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String MORE_USER = "存在多个同邮箱账户，请联系官方处理";

    @Autowired
    private AccountService accountService;

    //personal
    @Autowired
    private CreditService creditService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        try {
            User user = accountService.login(userForm);

            if (user == null) {
                return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
            }
            return ResponseVO.buildSuccess(user);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(MORE_USER);
        }

    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
//        System.out.println(userVO.getUserName());
//        System.out.println(userVO.getBirthday());


        return accountService.registerAccount(userVO);

    }


    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @GetMapping("/{id}/name")
    public ResponseVO getNameById(@PathVariable int id){
        return ResponseVO.buildSuccess(accountService.getNameById(id));
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }


    //personal
    @GetMapping("/{userId}/getUserClient")
    public ResponseVO retrieveCreditRecord(@PathVariable Integer userId){
        return ResponseVO.buildSuccess(creditService.retrieveCreditRecord(userId));
    }
}
