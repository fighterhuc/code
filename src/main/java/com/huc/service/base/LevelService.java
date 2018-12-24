package com.huc.service.base;

import com.huc.config.Dao;
import com.huc.domain.base.LevelBean;
import com.huc.util.Page;
import com.huc.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LevelService {
    private static final String CLASSNAME = LevelService.class.getName()+".";
    @Autowired
    private Dao dao;

    public Object listLevel(Map<String,Object> params,Page page){
        return dao.page(CLASSNAME,"listLevels",params,page);
    }
    public LevelBean getLevelById(Long id){
        return dao.get(CLASSNAME,"getLevelById",id);
    }
    public List<LevelBean> getLevelList(Map<String,Object> params){
        return dao.getList(CLASSNAME,"listLevels",params);
    }
    /**
     * 添加用户信息
     * @param userBean
     * @return
     */
    public Map<String,Object> addLevel(LevelBean userBean){
        dao.insert(CLASSNAME,"addLevel",userBean);
        return ResultUtil.success("保存成功!");
    }
    /**
     * 删除用户信息
     * @param id
     * @return
     */
    public Map<String,Object> delLevel(String id){
        dao.delete(CLASSNAME,"delLevel",id);
        return ResultUtil.success("删除成功!");
    }

}
