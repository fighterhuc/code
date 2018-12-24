package com.huc.service.report;

import com.huc.config.Dao;
import com.huc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PmsReportService {
    private static final String CLASSNAME = PmsReportService.class.getName()+".";
    @Autowired
    private Dao dao;
    public Object listPmsReportList(Map<String,Object> params, Page page){
        return dao.page(CLASSNAME,"listPmsReportList",params,page);
    }
}
