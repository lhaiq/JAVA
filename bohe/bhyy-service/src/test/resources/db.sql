CREATE TABLE `doctor` (
  `doctor_id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `real_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `icon` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `gender` int(11) DEFAULT NULL COMMENT '性别 0-女 1-男',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `hospital_name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '医院名字',
  `department` int(11) DEFAULT NULL COMMENT '科室',
  `title` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '职称',
  `adept` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '擅长内容',
  `education` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学历',
  `work_year` int(11) DEFAULT NULL COMMENT '工作年限',
  `intro` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '简介',
  `id_icon` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证正面照',
  `job_icon` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '职业资格证',
  `doctor_icon` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '医师资格证',
  `status` int(11) DEFAULT '0' COMMENT '医师状态 0-已注册,待认证 1-认证成功 2-认证失败 3-停诊中',
  `age` int(11) DEFAULT NULL,
  `service_item` int(11) DEFAULT NULL,
  `is_show` int(11) DEFAULT '0',
  `assistant_icon` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `source` int(11) DEFAULT '0',
  `add_date` date DEFAULT NULL,
  `failure_reason` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;