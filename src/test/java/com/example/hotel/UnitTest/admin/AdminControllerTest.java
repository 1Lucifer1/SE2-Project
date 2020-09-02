package com.example.hotel.UnitTest.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.controller.admin.AdminController;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    AdminService adminService;
    @Autowired
    AdminController adminController;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    EncryptionService encryptionService;

    private UserForm user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //init a new user
        user = new UserForm();
        user.setEmail("1234@qq.com");
        user.setPassword("12345678");
        //user = encryptionService.encryptionOfUserForm(user);
    }

    @Test
    @Transactional
    @Rollback
    public void testAddManager() throws Exception {
        assert (adminService.addManager(user).getSuccess());
        ResponseVO managersList = adminController.getAllManagers();
        assert (managersList.getSuccess());
        List<User> list = (List)managersList.getContent();
        boolean test = false;
        for(int i = 0; i < list.size() ; i++){
            if(list.get(i).getEmail().equals(user.getEmail())) test = true;
        }
        assert (test);
    }

    @Test
    @Transactional
    @Rollback
    public void testGetAllManagers() throws Exception {
        ResponseVO managersList = adminController.getAllManagers();
        assert (managersList.getSuccess());
        List<User> list = (List) managersList.getContent();
        for(int i = 0 ; i < list.size() ; i++){
            Assert.assertEquals(list.get(i).getUserType(), UserType.HotelManager);
            Assert.assertNotNull(accountMapper.getAccountById(list.get(i).getId()));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testGetAllClients() throws Exception {
        ResponseVO clientsList = adminController.getAllClients();
        assert (clientsList.getSuccess());
        List<User> list = (List) clientsList.getContent();
        for(int i = 0 ; i < list.size() ; i++){
            Assert.assertEquals(list.get(i).getUserType(), UserType.Client);
            Assert.assertNotNull(accountMapper.getAccountById(list.get(i).getId()));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteUser() throws Exception {
        testAddManager();
        UserForm user1 = encryptionService.encryptionOfUserForm(user);
        User user2 = encryptionService.decryptionOfUser(accountMapper.getAccountByEmail(user1.getEmail()));
        Assert.assertNotNull(user2);
        assert (adminController.deleteUser(accountMapper.getAccountByEmail(user1.getEmail()).getId()).getSuccess());
        Assert.assertNull(accountMapper.getAccountByEmail(user.getEmail()));
    }

    @Test
    @Transactional
    @Rollback
    public void testRechargeCredit() throws Exception {
        //init a new user
        User user = new User();
        user.setEmail("1234@qq.com");
        user.setPassword("12345678");
        user.setUserType(UserType.Client);
        user.setBirthday("2000-01-01");
        user.setCredit(100);
        user.setUserName("aaa");
        user.setPhoneNumber("123");
        accountMapper.createNewAccount(user);
        user.setId(accountMapper.getAccountByEmail(user.getEmail()).getId());
        adminController.rechargeCredit(10,user.getId());
        Assert.assertEquals(accountMapper.getAccountById(user.getId()).getCredit(), 1100.0, 0.5);
        adminController.rechargeCredit(-20,user.getId());
        Assert.assertEquals(accountMapper.getAccountById(user.getId()).getCredit(), -900.0, 0.5);
    }
}
