package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String EMAIL_EXIST = "该邮箱已注册";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String REGISTER_SUCCESS= "注册成功";
    private final static String MORE_USER = "存在多个同邮箱账户，请联系官方处理";
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private EncryptionService encryptionService;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        //加密！！！！！！！
        user=encryptionService.encryptionOfUser(user);

        try {
            if(accountMapper.getAccountByEmail(user.getEmail())!=null){
                return ResponseVO.buildFailure(EMAIL_EXIST);
            }
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(REGISTER_SUCCESS);
    }

    @Override
    public User login(UserForm userForm) {
        //加密
        userForm=encryptionService.encryptionOfUserForm(userForm);
        User user = accountMapper.getAccountByEmail(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        //解密
        user=encryptionService.decryptionOfUser(user);
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        //解密
        user=encryptionService.decryptionOfUser(user);
        return user;
    }

    @Override
    public String getNameById(int id) {
        //解密
        return encryptionService.decryptionOfData(accountMapper.getNameById(id));
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        //加密
        password=encryptionService.encryptionOfData(password);
        username=encryptionService.encryptionOfData(username);
        phonenumber=encryptionService.encryptionOfData(phonenumber);

        try {
            accountMapper.updateAccount(id, password, username, phonenumber);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void updateCredit(int id, double credit) {
        accountMapper.updateCredit(id,credit);
    }

    @Override
    public User getAccountByEmail(String email) {
        return accountMapper.getAccountByEmail(email);
    }
}
