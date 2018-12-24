package com.huc.service.operation;

import com.huc.config.Dao;
import com.huc.domain.base.LevelBean;
import com.huc.domain.operation.OperationRecordBean;
import com.huc.service.base.LevelService;
import com.huc.util.Page;
import com.huc.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OperationRecordService {
    private static final String CLASSNAME = OperationRecordService.class.getName()+".";
    @Autowired
    private Dao dao;
    @Autowired
    private LevelService levelService;

    public Object listOperationRecord(Map<String,Object> params,Page page){
        return dao.page(CLASSNAME,"listOperationRecords",params,page);
    }
    /**
     * @param operationRecordBean
     * @return
     */
    public Map<String,Object> addOperationRecord(OperationRecordBean operationRecordBean){
        LevelBean levelBean = levelService.getLevelById(operationRecordBean.getLevelId());
        if((operationRecordBean.getTime()-(levelBean.getBaseTime()*1.0))>0){
            operationRecordBean.setThisTimeScore(levelBean.getBaseTime()*levelBean.getLevelScore()
                    +(operationRecordBean.getTime()-(levelBean.getBaseTime()*1.0))*(levelBean.getLevelScore()+levelBean.getAddScore()));
        }else{
            operationRecordBean.setThisTimeScore(operationRecordBean.getTime()*levelBean.getLevelScore());
        }
        dao.insert(CLASSNAME,"addOperationRecord",operationRecordBean);
        return ResultUtil.success("保存成功!");
    }
}
