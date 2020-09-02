package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){
        return adminService.addManager(userForm);
    }

    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    //personal
    @PostMapping("/getAllClients")
    public ResponseVO getAllClients(){
        //System.out.print("进入后端了");
        return ResponseVO.buildSuccess(adminService.getAllClients());
    }

    /**
     * **************注意！！！删除酒店工作人员会导致其管理的酒店被删除！！！！
     * @param userId
     * @return
     */
    @PostMapping("/{userId}/deleteUser")
    public ResponseVO deleteUser(@PathVariable int userId){
        return adminService.deleteUser(userId);
    }

    @PostMapping("/{rechargePoint}/{userId}/addClient")
    public ResponseVO rechargeCredit(@PathVariable int rechargePoint,@PathVariable int userId){
        System.out.println("进入后端了");
        System.out.println(rechargePoint);
        System.out.println(userId);

        return adminService.rechargeCredit(rechargePoint,userId);
    }
}
