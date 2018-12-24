package com.huc.controller.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController public class LoginController {
    @RequestMapping("/login")
    public Object index(@RequestParam Map<String,Object> params) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =new UsernamePasswordToken(params.get("username").toString(),params.get("password").toString());
        Map<String,Object> map = new HashMap<>();
        try{
            subject.login(token);
            map.put("success",true);
            map.put("user","{\"username\":\""+params.get("username")+"\"}");
        }catch (AuthenticationException e){
            map.put("success",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }
}
