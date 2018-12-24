package com.huc.controller.operation;

import com.huc.domain.operation.OperationRecordBean;
import com.huc.service.operation.OperationRecordService;
import com.huc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class OperationRecordController {
    @Autowired
    private OperationRecordService levelService;
    @RequestMapping("/listOperationRecord")
    public Object listOperationRecord(@RequestParam Map<String,Object> params, HttpServletRequest request){
        return levelService.listOperationRecord(params, Page.get(request));
    }
    @RequestMapping("/addOperationRecord")
    public Map<String,Object> addOperationRecord(@ModelAttribute OperationRecordBean operationRecordBean){
        return levelService.addOperationRecord(operationRecordBean);
    }
}
