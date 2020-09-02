package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.EncryptionService;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encryptionOfData(String data) {
        if(data==null){
            return null;
        }
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    @Override
    public String decryptionOfData(String data) {
        if(data==null){
            return null;
        }
        return new String(Base64.getDecoder().decode(data));
    }

    @Override
    public User encryptionOfUser(User user) {
        if (user==null){
            return null;
        }
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setEmail(encryptionOfData(user.getEmail()));
        user1.setPassword(encryptionOfData(user.getPassword()));
        user1.setPhoneNumber(encryptionOfData(user.getPhoneNumber()));
        user1.setUserName(encryptionOfData(user.getUserName()));
        return user1;
    }

    @Override
    public User decryptionOfUser(User user) {
        if(user==null){
            return null;
        }
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setEmail(decryptionOfData(user.getEmail()));
        user1.setPassword(decryptionOfData(user.getPassword()));
        user1.setPhoneNumber(decryptionOfData(user.getPhoneNumber()));
        user1.setUserName(decryptionOfData(user.getUserName()));
        return user1;
    }

    @Override
    public UserForm encryptionOfUserForm(UserForm userForm) {
        if(userForm==null){
            return null;
        }

        UserForm userForm1 = new UserForm();
        BeanUtils.copyProperties(userForm, userForm1);
        userForm1.setEmail(encryptionOfData(userForm.getEmail()));
        userForm1.setPassword(encryptionOfData(userForm.getPassword()));
        return userForm1;
    }

    @Override
    public UserForm decryptionOfUserForm(UserForm userForm) {
        if(userForm==null){
            return null;
        }
        UserForm userForm1 = new UserForm();
        BeanUtils.copyProperties(userForm, userForm1);
        userForm1.setEmail(decryptionOfData(userForm.getEmail()));
        userForm1.setPassword(decryptionOfData(userForm.getPassword()));
        return userForm1;
    }
}
