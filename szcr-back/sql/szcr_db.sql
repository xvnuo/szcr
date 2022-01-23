use ry;

-- ----------------------------
-- 1、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=1 comment = '用户信息表';

-- ----------------------------
-- 2、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 3、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 4、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 5、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 6、考勤记录
-- ----------------------------
drop table if exists attend_record;
create table attend_record (
                               record_id         bigint(20)    not null auto_increment      comment '记录ID',
                               user_id           bigint(20)    not null                     comment '用户ID',
                               user_name         varchar(30)                                comment '用户名',
                               attend_date       date          not null                     comment '考勤日期',
                               weekday_num       int                                        comment '星期几',
                               is_workday        char(1)                                    comment '是否工作日',
                               attend_year       int                                        comment '年份',
                               attend_month      int                                        comment '月份',
                               attend_hour       double                                     comment '考勤时长',
                               schedule_sort     int            default 0                   comment '排班序号',
                               on_time           datetime                                   comment '上班时间',
                               on_status         char(1)                                    comment '上班状态',
                               off_time          datetime                                   comment '下班时间',
                               off_status        char(1)                                    comment '下班状态',
                               create_by         varchar(64)     default ''                 comment '创建者',
                               create_time       datetime                                   comment '创建时间',
                               update_by         varchar(64)     default ''                 comment '更新者',
                               update_time       datetime                                   comment '更新时间',
                               remark            varchar(500)    default '无'               comment '备注',
                               primary key (record_id)
) engine=innodb auto_increment=1 comment = '考勤记录表';


-- ----------------------------
-- 7、节假日信息表
-- ----------------------------
drop table if exists attend_hoilday;
create table attend_holiday (
  holiday_id         bigint(20)      not null auto_increment    comment '节假日ID',
  name               varchar(30)     not null                   comment '节假日名称',
  begin_time         datetime        not null                   comment '开始时间',
  end_time           datetime        not null                   comment '结束时间',
  status             char(1)         not null                   comment '假日状态（0正常 1停用）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (holiday_id)
) engine=innodb auto_increment=1 comment = '节假日信息表';


-- ----------------------------
-- 8、考勤规则
-- ID，名称，类型，固定打卡(起始时间、工作日)，弹性打卡最小工时，排班时刻表（班次编号，开始时间，结束时间）
-- ----------------------------
drop table if exists attend_rule;
create table attend_rule (
  rule_id           bigint(20)      not null auto_increment    comment '编号',
  rule_name         varchar(30)     not null                   comment '名称',
  rule_type         char(1)         not null                   comment '考勤类型（1固定 2弹性 3排班）',
  on_time           time                                       comment '上班时间',
  off_time          time                                       comment '下班时间',
  work_hour         int                                        comment '每日工时',
  work_days         varchar(30)                                comment '工作日',
  status            char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment  '备注',
  primary key (rule_id)
) engine=innodb auto_increment=1 comment = '考勤规则表';


-- ----------------------------
-- 9、排班表
-- ----------------------------
drop table if exists attend_schedule;
create table attend_schedule (
  schedule_id       bigint(20)      not null auto_increment    comment '班次编号',
  rule_id           bigint(20)      not null                   comment '规则ID',
  schedule_name     varchar(30)     not null                   comment '班次名称',
  work_day          int             not null                   comment '工作日',
  on_time           time            not null                   comment '开始时间',
  off_time          time            not null                   comment '结束时间',
  status            char(1)         default '0'                comment '状态（0正常 1停用）',
  remark            varchar(500)    default null               comment  '备注',
  primary key (schedule_id)
) engine=innodb auto_increment=1 comment = '排班表';


-- ----------------------------
-- 9、考勤统计表
-- ----------------------------
drop table if exists attend_record;
create table attend_record (
                               record_id         bigint(20)    not null auto_increment      comment '记录ID',
                               user_id           bigint(20)    not null                     comment '用户ID',
                               user_name         varchar(30)                                comment '用户名',
                               attend_date       date          not null                     comment '考勤日期',
                               weekday_num       int                                        comment '星期几',
                               is_workday        char(1)                                    comment '是否工作日',
                               attend_month      varchar(30)   not null                     comment '考勤月份',
                               attend_hour       double                                     comment '考勤时长',
                               schedule_sort     int           default 0                    comment '排班序号',
                               on_time           datetime                                   comment '上班时间',
                               on_status         char(1)                                    comment '上班状态',
                               off_time          datetime                                   comment '下班时间',
                               off_status        char(1)                                    comment '下班状态',
                               create_by         varchar(64)   default ''                   comment '创建者',
                               create_time       datetime                                   comment '创建时间',
                               update_by         varchar(64)   default ''                   comment '更新者',
                               update_time       datetime                                   comment '更新时间',
                               remark            varchar(500)  default '无'                 comment '备注',
                               primary key (record_id)
) engine=innodb auto_increment=1 comment = '考勤记录表';



drop table if exists attend_special;
create table attend_special (
                                special_id        bigint(20)    not null auto_increment      comment '异常考勤ID',
                                user_id           bigint(20)    not null                     comment '用户ID',
                                user_name         varchar(30)   not null                     comment '用户名',
                                attend_type       char(1)                                    comment '考勤类型',
                                start_time        datetime                                   comment '开始时间',
                                end_time          datetime                                   comment '结束时间',
                                attend_hour       double                                     comment '时长',
                                create_by         varchar(64)     default ''                 comment '创建者',
                                create_time       datetime                                   comment '创建时间',
                                update_by         varchar(64)     default ''                 comment '更新者',
                                update_time       datetime                                   comment '更新时间',
                                remark            varchar(500)    default '无'               comment '备注',
                                primary key (special_id)
) engine=innodb auto_increment=1 comment = '异常考勤';


drop table if exists attend_statistics;
create table attend_statistics (
                                   statistics_id     bigint(20)      not null auto_increment    comment '统计编号',
                                   attend_month      varchar(30)     not null                   comment '考勤月份',
                                   user_id           bigint(20)      not null                   comment '用户编号',
                                   user_name         varchar(30)                                comment '用户名',
                                   rule_id           bigint(20)                                 comment '规则编号',
                                   rule_name         varchar(30)                                comment '规则名称',
                                   attend_days       double          default 0                  comment '实到天数',
                                   normal_days       double          default 0                  comment '正常天数',
                                   outside_days      double          default 0                  comment '外勤天数',
                                   absence_days      double          default 0                  comment '缺勤天数',
                                   leave_days        double          default 0                  comment '请假天数',
                                   late_times        int             default 0                  comment '迟到次数',
                                   early_times       int             default 0                  comment '早退次数',
                                   over_hours        double          default 0                  comment '加班时长',
                                   primary key (statistics_id)
) engine=innodb auto_increment=1 comment = '月度考勤统计';
