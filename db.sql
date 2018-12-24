CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `department` varchar(100) NOT NULL COMMENT '科室',
  `phone` varchar(30) NOT NULL COMMENT '手机号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8
CREATE TABLE `sys_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` varchar(10) NOT NULL COMMENT '级别',
  `base_score` decimal(2,1) NOT NULL COMMENT '基础分值',
  `base_time` int(10) NOT NULL COMMENT '基础时间',
  `add_score` decimal(2,1) NOT NULL COMMENT '超时加分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
CREATE TABLE `pms_operation_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '主刀医生ID',
  `department` varchar(50) NOT NULL COMMENT '科室',
  `operating_room` varchar(50) NOT NULL COMMENT '手术间',
  `operation_date` date NOT NULL COMMENT '手术日期',
  `patient_name` varchar(10) NOT NULL COMMENT '患者姓名',
  `in_room_time` datetime NOT NULL COMMENT '入室时间',
  `out_room_time` datetime NOT NULL COMMENT '出室时间',
  `time` decimal(5,2) NOT NULL COMMENT '手术时间',
  `procedure` varchar(20) NOT NULL COMMENT '术式',
  `level_id` bigint(20) NOT NULL COMMENT '级别',
  `qj_nurse` varchar(20) NOT NULL COMMENT '器械护士',
  `xh_nurse` varchar(20) NOT NULL COMMENT '巡回护士',
  `this_time_score` decimal(5,2) NOT NULL COMMENT '本次得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8