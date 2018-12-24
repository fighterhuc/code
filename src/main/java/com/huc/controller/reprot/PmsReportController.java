package com.huc.controller.reprot;

import com.huc.service.report.PmsReportService;
import com.huc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class PmsReportController {
    @Autowired
    private PmsReportService pmsReportService;
    @RequestMapping("/listPmsReportList")
    public Object listPmsReportList(@RequestParam Map<String,Object> params, HttpServletRequest request){
        return pmsReportService.listPmsReportList(params, Page.get(request));
    }
}
