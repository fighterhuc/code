package com.huc.controller.base;

import com.huc.domain.base.LevelBean;
import com.huc.service.base.LevelService;
import com.huc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LevelController {
    @Autowired
    private LevelService levelService;
    @RequestMapping("/listLevel")
    public Object listLevel(@RequestParam Map<String,Object> params, HttpServletRequest request){
        return levelService.listLevel(params, Page.get(request));
    }
    @RequestMapping("/getLevelList")
    public Object getLevelList(@RequestParam Map<String,Object> params){
        return levelService.getLevelList(params);
    }
    @RequestMapping("/addLevel")
    public Map<String,Object> addLevel(@ModelAttribute LevelBean levelBean){
        return levelService.addLevel(levelBean);
    }
    @RequestMapping("/delLevel")
    public Map<String,Object> delLevel(String id){
        return levelService.delLevel(id);
    }
}
