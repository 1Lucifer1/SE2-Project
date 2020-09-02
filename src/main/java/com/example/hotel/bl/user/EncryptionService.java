package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;

public interface EncryptionService {

    /**
     * 均采用 Base64 对数据进行加密与解密
     */


    /**
     * 对data进行加密
     * @param data
     * @return
     */
    String encryptionOfData(String data);

    /**
     * 对data进行解密
     * @param data
     * @return
     */
    String decryptionOfData(String data);

    /**
     * 对user的账号、密码、姓名（名称）、联系方式 加密
     * @param user
     * @return
     */
    User encryptionOfUser(User user);

    /**
     * 对user的账号、密码、姓名（名称）、联系方式 解密
     * @param user
     * @return
     */
    User decryptionOfUser(User user);

    /**
     * 对user的账号、密码 加密
     * @param userForm
     * @return
     */
    UserForm encryptionOfUserForm(UserForm userForm);

    /**
     * 对user的账号、密码 解密
     * @param userForm
     * @return
     */
    UserForm decryptionOfUserForm(UserForm userForm);

}
