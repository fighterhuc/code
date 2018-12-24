package com.huc.domain.operation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OperationRecordBean {
    private Long id;
    private String username;
    private Long userId;
    private String department;
    private String operatingRoom;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date operationDate;
    private String patientName;
    private Date inRoomTime;
    private Date outRoomTime;
    private Double time;
    private String procedure;
    private Long levelId;
    private String levelName;
    private String qjNurse;
    private String xhNurse;
    private Double thisTimeScore;
}
