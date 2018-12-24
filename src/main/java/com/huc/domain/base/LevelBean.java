package com.huc.domain.base;

import lombok.Data;

@Data
public class LevelBean {
    private Long id;
    private String levelName;
    private Double levelScore;
    private Integer baseTime;
    private Double addScore;
}
