use ry;

-- ----------------------------
-- 1、考勤记录
-- ----------------------------
drop table if exists attend_record;
create table attend_record (
  record_id         bigint(20)      not null auto_increment    comment '考勤ID',
  user_id           varchar(30)     not null                   comment '用户ID',
  status            char(1)         default '0'                comment '考勤状态（0正常 1停用）',
  date              date                                       comment '考勤日期',
  on_time           datetime                                   comment '上班时间',
  off_time          datetime                                   comment '下班时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '考勤记录表';
