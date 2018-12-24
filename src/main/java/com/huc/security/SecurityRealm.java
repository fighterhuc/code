package com.huc.security;

import com.huc.domain.base.UserBean;
import com.huc.service.base.UserService;
import com.huc.util.SpringBeanFactoryUtils;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;
@Data
public class SecurityRealm extends AuthorizingRealm {
    private UserService userService;
    /**
     * 为当前用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return  authorizationInfo;
    }

    /**
     * 登陆认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        System.out.println("当前认证的用户为:"+token);
        Map<String,Object> params = new HashMap<>();
        params.put("username",token.getUsername());
        params.put("password",new String(token.getPassword()));
        if(userService==null){
            userService= SpringBeanFactoryUtils.getBean(UserService.class);
        }
        UserBean user = userService.get(params);
        if(user!=null){
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
            return new SimpleAuthenticationInfo(token.getUsername(),token.getPassword(),getName());
        }else{
            throw  new AuthenticationException("登陆失败");
        }
    }
}
