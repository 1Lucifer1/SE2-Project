package com.example.hotel.UnitTest.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CreditService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.controller.user.AccountController;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Credit;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserInfoVO;
import com.example.hotel.vo.UserVO;
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

import java.awt.image.Raster;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    AccountService accountService;
    @Autowired
    CreditService creditService;
    @Autowired
    AccountController accountController;
    @Autowired
    EncryptionService encryptionService;
    @Autowired
    AccountMapper accountMapper;

    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setEmail("12345@qq.com");
        user.setPassword("1234567890");
        user.setUserType(UserType.HotelManager);
        user.setBirthday("2000-01-01");
        user.setCredit(100);
        user.setUserName("aaa");
        user.setPhoneNumber("123");
    }

    @Test
    @Transactional
    @Rollback
    public void testLogin() throws Exception {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        assert accountController.registerAccount(userVO).getSuccess();

        UserForm userForm = new UserForm();
        userForm.setEmail(user.getEmail());
        userForm.setPassword(user.getPassword());
        ResponseVO result = accountController.login(userForm);
        assert result.getSuccess();
        User user1 = (User) result.getContent();
        //Assert.assertEquals(user1.getId(), user.getId());
        Assert.assertEquals(user1.getEmail(), user.getEmail());
        Assert.assertEquals(user1.getPassword(), user.getPassword());
        Assert.assertEquals(user1.getUserName(), user.getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void testRegisterAccount() throws Exception {
        UserVO userVO = new UserVO();

        BeanUtils.copyProperties(user, userVO);
        assert accountController.registerAccount(userVO).getSuccess();
        User user1 = encryptionService.decryptionOfUser(accountMapper.getAccountByEmail(encryptionService.encryptionOfData(user.getEmail())));
        user.setId(user1.getId());
        Assert.assertEquals(user.getUserType(), user1.getUserType());
        Assert.assertEquals(user.getPhoneNumber(), user1.getPhoneNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetUserInfo() throws Exception {
        testRegisterAccount();
        ResponseVO result = accountController.getUserInfo(user.getId());
        assert result.getSuccess();
        User user1 = (User) result.getContent();
        Assert.assertEquals(user1.getUserName(), user.getUserName());
        Assert.assertEquals(user1.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(user1.getUserType(), user.getUserType());
        Assert.assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetNameById() throws Exception {
        testRegisterAccount();
        ResponseVO result = accountController.getNameById(user.getId());
        assert result.getSuccess();
        String name = (String) result.getContent();
        Assert.assertEquals(name, user.getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateInfo() throws Exception {
        testRegisterAccount();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserName("456789");
        userInfoVO.setPassword("123456789");
        userInfoVO.setPhoneNumber("123");
        assert accountController.updateInfo(userInfoVO, user.getId()).getSuccess();
        User user1 = encryptionService.decryptionOfUser(accountMapper.getAccountByEmail(encryptionService.encryptionOfData(user.getEmail())));
        Assert.assertEquals(userInfoVO.getUserName(), user1.getUserName());
        Assert.assertEquals(userInfoVO.getPassword(), user1.getPassword());
        Assert.assertEquals(userInfoVO.getPhoneNumber(), user1.getPhoneNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void testRetrieveCreditRecord() throws Exception {
        testRegisterAccount();
        Credit credit = new Credit();
        credit.setActionType("0");
        credit.setChangeTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis())));
        credit.setCreditChange(10.0);
        credit.setCreditResult(10.0);
        credit.setUserId(user.getId());
        credit.setOrderId(0);
        creditService.addCreditRecord(credit);
        ResponseVO result = accountController.retrieveCreditRecord(user.getId());
        assert result.getSuccess();
        List<Credit> list = (List) result.getContent();
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getActionType(), credit.getActionType());
        Assert.assertEquals(list.get(0).getCreditChange(), credit.getCreditChange(), 0.5);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
