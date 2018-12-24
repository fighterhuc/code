package com.huc.service.base;

import com.huc.config.Dao;
import com.huc.domain.base.UserBean;
import com.huc.util.Page;
import com.huc.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private static final String CLASSNAME = UserService.class.getName()+".";
    @Autowired
    private Dao dao;

    public Object listUser(Map<String,Object> params,Page page){
        return dao.page(CLASSNAME,"listUsers",params,page);
    }
    public List<UserBean> listUsers(){
        return dao.getList(CLASSNAME,"listUsers",new HashMap<>());
    }
    public UserBean get(Map<String,Object> params){
        return dao.get(CLASSNAME,"listUsers",params);
    }

    /**
     * 添加用户信息
     * @param userBean
     * @return
     */
    public Map<String,Object> addUser(UserBean userBean){
        Map<String,Object> params = new HashMap<>();
        params.put("username",userBean.getUsername());
        UserBean user = get(params);
        if(user!=null){
            return ResultUtil.failure("用户名已存在!");
        }
        dao.insert(CLASSNAME,"addUser",userBean);
        return ResultUtil.success("保存成功!");
    }
    /**
     * 添加用户信息
     * @param userBean
     * @return
     */
    public Map<String,Object> editUser(UserBean userBean){
        dao.update(CLASSNAME,"editUser",userBean);
        return ResultUtil.success("编辑成功!");
    }
    /**
     * 删除用户信息
     * @param id
     * @return
     */
    public Map<String,Object> delUser(String id){
        dao.delete(CLASSNAME,"delUser",id);
        return ResultUtil.success("删除成功!");
    }

}
