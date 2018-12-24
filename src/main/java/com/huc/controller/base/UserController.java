package com.huc.controller.base;

import com.huc.domain.base.UserBean;
import com.huc.service.base.UserService;
import com.huc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/listUser")
    public Object listUser(@RequestParam Map<String,Object> params, HttpServletRequest request){
        return userService.listUser(params, Page.get(request));
    }
    @RequestMapping("/listUsers")
    public Object listUsers(){
        return userService.listUsers();
    }
    @RequestMapping("/addUser")
    public Map<String,Object> addUser(@ModelAttribute UserBean userBean){
        return userService.addUser(userBean);
    }
    @RequestMapping("/editUser")
    public Map<String,Object> editUser(@ModelAttribute UserBean userBean){
        return userService.editUser(userBean);
    }
    @RequestMapping("/delUser")
    public Map<String,Object> delUser(String id){
        return userService.delUser(id);
    }
}
