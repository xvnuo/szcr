# --------------
# # 命名规范
# ## 对象命名规范
#
# 命名建议使用具有意义的英文词汇，词汇中间以下划线分隔；
# 命名只能使用英文字母、数字、下划线；
# 避免用 SQL 关键字，如 group，error，rank 等作为对象名；
# 建议所有的数据库对象使用小写字母；
# 所有的数据库对象的命名请注意标识符长度硬限制 ；
#
# ## 数据库命名规范
#
# 按照业务模块、产品线和访问权限隔离需求来创建数据库；
# 建议数据库名称不要超过 16 个字符，如：基础信息库（basic_info_db）、认证中心库（certify_center_db）；
#
# ## 表命名规范
#
# 同一业务或者模块的表尽可能使用相同的前缀，表名称尽可能表达含义；
# 多个单词以下划线分隔，不推荐超过32个字符；
# 建议对表的用途进行注释说明，以便于统一认识； 临时用统计表（tmp_t_crm_relation_0425） 备份表（bak_t_crm_relation_20170425）
# 只支持将 lower-case-table-names 值设为 2，即数字字典中记录的表名区分大小写，匹配查找表名时不区分大小写；
#
# ## 字段命名规范
#
# 字段命名需要表示其实际含义的英文单词或简写；
# 建议各表之间相同意义的字段应同名，并且一定使用相同的字段类型；
# 字段也尽量添加注释，枚举型需指明主要值的含义，如”0 - 离线，1 - 在线”；
# 布尔值列命名为 [is_描述]。如 member 表上表示为 enabled 的会员的列命名为 is_enabled；
# 字段名不建议超过 32 个字符；
# 表中的外键为其他表的名字+_id
#   — 例如，在 t_owner 中的 user.id 字段应为 user_id
#
# ## 语法规范
# 不建议使用 MySQL 特有语法。尽量使用标准 SQL 语法。
# -------------
start transaction;
create database if not exists szcr_db character set UTF8;
USE szcr_db;
-- ----------------------------
-- 用户表
-- ----------------------------
create table if not exists `t_user`
(
    `id`             bigint unsigned auto_increment,
    `phone_number`   varchar(15) unique key,
    `user_name`      varchar(20),
    `avatar_url`     varchar(2083) default null comment '头像',
    `real_name`      varchar(25)   default null comment '真实姓名',
    `id_card_number` char(18)      default null comment '身份证号',
    `created_time`   datetime,
    `update_time`    datetime,
    primary key (id)
    ) comment '用户表';

-- ----------------------------
-- 用户拓展信息表
-- ----------------------------
create table if not exists `t_user_extension`
(
    `id`           bigint unsigned auto_increment,
    `user_id`      bigint unsigned unique key,
    `age`          int comment '年龄',
    `sex`          enum ('male','female') default null comment '性别',
    `ethnic_group` varchar(5)             default null comment '民族',
    primary key (id)
    ) comment '用户拓展信息表';

-- ----------------------------
-- 用户认证信息表
-- TODO 需要商榷
-- ----------------------------
create table if not exists `t_user_oauth`
(
    `id`               bigint unsigned auto_increment,
    `user_id`          bigint unsigned unique key,
    `wechat_open_id`   varchar(8) unique key,
    `wechat_union_id`  varchar(8) unique key,
    `wechat_user_name` varchar(256),
    `session_key`      varchar(256),
    `login_status`     enum ('signed', 'oauth'),
    primary key (id)
    ) comment '微信认证信息';

# 蓝牙锁管理层级

-- ----------------------------
-- 蓝牙锁信息表
-- TODO 全部需要商榷
-- ----------------------------
create table if not exists `t_lock`
(
    `id`            bigint unsigned auto_increment,
    `key_id`        bigint unsigned unique key,
    `blue_tooth_id` varchar(20),
    `room`          varchar(20) comment '房号',
    `name`          varchar(20) comment '别名',
    `description`   text default null comment '备注',
    `floor_id`      bigint unique key,
    primary key (id)
    ) comment '蓝牙锁信息表';

create table if not exists `t_floor`
(
    `id`          bigint unsigned,
    `name`        varchar(20),
    `description` text default null,
    `building_id` bigint unsigned,
    primary key (id)
    ) comment '楼层';

create table if not exists `t_unit`
(
    `id`          bigint unsigned,
    `name`        varchar(20),
    `description` text default null,
    `building_id` bigint unsigned,
    primary key (id)
    ) comment '单元';

create table if not exists `t_building`
(
    `id`          bigint unsigned,
    `name`        varchar(20),
    `description` text default null,
    `area_id`     bigint unsigned,
    primary key (id)
    ) comment '楼栋';

create table if not exists `t_area`
(
    `id`          bigint unsigned,
    `name`        varchar(20),
    `description` text default null,
    `item_id`     bigint unsigned,
    primary key (id)
    ) comment '区域';

create table if not exists `t_item`
(
    `id`          bigint unsigned,
    `name`        varchar(20),
    `description` text default null,
    primary key (id)
    ) comment '项目';

create table if not exists `t_unlock_record`
(
    id            bigint,
    lock_id       bigint   not null,
    user_id       bigint   not null,
    unlock_result int comment '错误码: {0: 成功}',
    unlock_time   datetime not null,
    primary key (id)
    ) comment '开门记录';

# ## 身份表

create table if not exists `t_authority_owner`
(
    user_id bigint not null,
    lock_id bigint not null,
    primary key (user_id, lock_id)
    ) comment '所有权';

create table if not exists `t_authority_renter`
(
    user_id     bigint  not null,
    lock_id     bigint  not null,
    is_active   boolean not null,
    begin_time  datetime,
    expire_time datetime,
    primary key (user_id, lock_id)
    ) comment '租客';

create table if not exists `t_authority_member`
(
    user_id   bigint  not null,
    lock_id   bigint  not null,
    is_active boolean not null,
    owner_id  bigint  not null, # 一个人可以是多个家庭的成员
    primary key (owner_id, user_id, lock_id)
    ) comment '成员';

create table if not exists `t_authority_visitor`
(
    user_id      bigint  not null,
    lock_id      bigint  not null,
    is_active    boolean not null,
    remain_count int      default 0,
    begin_time   datetime default null,
    expire_time  datetime default null,
    primary key (user_id, lock_id)
    ) comment '访客';

create table if not exists `t_authority_record`
(
    user_id          bigint not null,
    lock_id          bigint not null,
    before_authority enum ('owner','renter','member','visitor') default null,
    after_authority  enum ('owner','renter','member','visitor') default null,
    update_time      datetime                                   default NOW(),
    primary key (user_id, lock_id)
    ) comment '权限变更';

# TODO 管理员信息表
create table if not exists `t_administrator`
(
    id        bigint unsigned,
    username  varchar(25)  not null,
    password  varchar(256) not null,
    ranking   enum ('lock','floor','building','unit','item') comment '管理级别',
    manage_id bigint comment '管理区域的id',
    primary key (id)
    ) comment '管理员身份';

# ## 认证表

create table if not exists `t_certification`
(
    id           bigint unsigned auto_increment,
    user_id      bigint      not null,
    real_name    varchar(25) not null,
    id_card_name char(18)    not null,
    operator_id  bigint   default null comment '审批管理员id',
    is_agree     boolean  default null comment '是否审批通过',
    create_time  datetime default now() comment '申请创建时间',
    finish_time  datetime default null comment '审批时间',
    primary key (id)
    ) comment '身份认证审批流';

create table if not exists `t_lock_approval`
(
    id          bigint unsigned auto_increment,
    user_id     bigint not null,
    lock_id     bigint not null,
    operator_id bigint   default null,
    is_agree    boolean  default null,
    create_time datetime default now(),
    finish_time datetime default null,
    primary key (id)
    ) comment '锁授权审批';

create table if not exists `t_poster`
(
    id         bigint unsigned auto_increment,
    title      varchar(20),
    content    text,
    issue_time datetime,
    primary key (id)
    ) comment '告示';

create table if not exists `t_check_poster`
(
    id                    bigint unsigned auto_increment,
    title                 varchar(20),
    content               text,
    author_id             bigint not null,
    member_id             bigint not null,
    issue_time            datetime default now(),
    is_proprietor_agree   boolean  default null,
    proprietor_agree_time datetime default null,
    is_admin_agree        boolean  default null,
    admin_agree_time      datetime default null,
    primary key (id)
    ) comment '申请表';

create table if not exists `t_sublet_poster`
(
    id                    bigint unsigned auto_increment,
    title                 varchar(20),
    content               text,
    old_renter_id         bigint not null,
    new_renter_id         bigint not null,
    issue_time            datetime default now(),
    is_proprietor_agree   boolean  default null,
    proprietor_agree_time datetime default null,
    is_admin_agree        boolean  default null,
    admin_agree_time      datetime,
    primary key (id)
    ) comment '转租申请';

commit;
