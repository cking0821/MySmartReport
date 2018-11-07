-- 用户
insert into test_report.SYS_USER (ID, BIRTHDAY, CREATE_TIME, CREATE_USER_ID, NICK_NAME, ORG_ID, ROLE_ID, SALT, UPDATE_TIME, UPDATE_USER_ID, USER_DESCRIPTION, USER_MAIL, USER_NAME, USER_PWD, USER_ROLE, USER_STATUS, USER_TEL) values(
'1', NULL, '2018-11-06 03:15:14.581000', '1', 'root', '1', '1', 'zkzfwavxthgapjcufhai', '2018-11-06 03:15:14.581000', '1', NULL, 'root@admin.com', 'root', '{SHA-256}9mt+oB6R+q29VrbWHl8NFLCmUEAhF6lZMK87hJiRNZM=', 'ADMINISTRATOR', 'NORMAL', '10010001000');
commit;


-- 用户组
insert into test_report.sys_user_group (ID, CREATE_TIME, CREATE_USER_ID, DESCRIPTION, GROUP_CODE, GROUP_NAME, GROUP_STATUS, UPDATE_TIME, UPDATE_USER_ID) values
('1', '2018-01-01 00:00:00.000000', '1', '超级管理员组', 'ADMIN_GROUP', '超级管理员组', 'NORMAL', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_user_group (ID, CREATE_TIME, CREATE_USER_ID, DESCRIPTION, GROUP_CODE, GROUP_NAME, GROUP_STATUS, UPDATE_TIME, UPDATE_USER_ID) values
('2', '2018-01-01 00:00:00.000000', '1', '报表编辑组', 'REPORT_EDIT_GROUP', '报表编辑组', 'NORMAL', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_user_group (ID, CREATE_TIME, CREATE_USER_ID, DESCRIPTION, GROUP_CODE, GROUP_NAME, GROUP_STATUS, UPDATE_TIME, UPDATE_USER_ID) values
('3', '2018-01-01 00:00:00.000000', '1', '设计组', 'DESIGNER_GROUP', '设计组', 'NORMAL', '2018-01-01 00:00:00.000000', '1');
commit;

-- 角色表
insert into  test_report.sys_role (ID, ROLE_CODE, ROLE_DESCRIPTION, ROLE_NAME) values('1', 'ADMINI', '超级管理员', '超级管理员');
insert into  test_report.sys_role (ID, ROLE_CODE, ROLE_DESCRIPTION, ROLE_NAME) values('2', 'MANAGER', '普通管理员', '普通管理员');
insert into  test_report.sys_role (ID, ROLE_CODE, ROLE_DESCRIPTION, ROLE_NAME) values('3', 'DESIGNER', '设计管理员', '设计管理员');
insert into  test_report.sys_role (ID, ROLE_CODE, ROLE_DESCRIPTION, ROLE_NAME) values('4', 'OPERATOR', '操作员', '操作员');
insert into  test_report.sys_role (ID, ROLE_CODE, ROLE_DESCRIPTION, ROLE_NAME) values('5', 'GUEST', '访客', '访客');
commit;


-- 权限表
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('1', '2018-01-01 00:00:00.000000', '0', 'PROJECT_MANAGE', 'fa fa-dashboard', 'initProject', '项目管理', '1', 'MENU');
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('2', '2018-01-01 00:00:00.000000', '0', 'DATSSOURCE_MANAGE', 'fa fa-database', 'initDataSource', '数据源管理', '1', 'MENU');
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('3', '2018-01-01 00:00:00.000000', '0', 'TEMPLATE_MANAGE', 'fa fa-book', 'initTemplate', '模板管理', '1', 'MENU');
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('4', '2018-01-01 00:00:00.000000', '0', 'REPORT_MANAGE', 'fa fa-bar-chart', 'initReport', '报表管理', '1', 'MENU');
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('5', '2018-01-01 00:00:00.000000', '0', 'USER_MANAGE', 'fa fa-user', 'initUserList', '用户管理', '1', 'MENU');
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('6', '2018-01-01 00:00:00.000000', '0', 'ROLE_MANAGE', 'fa fa-user-secret', 'initRoleList', '角色管理', '1', 'MENU');
insert into test_report.SYS_RESOURCE (ID, CREATE_TIME, PARENT_ID, RESOURCE_Code, RESOURCE_CSS, RESOURCE_LINK, RESOURCE_NAME, RESOURCE_STATUS, RESOURCE_TYPE) values
('7', '2018-01-01 00:00:00.000000', '0', 'RESOURCE_MANAGE', 'fa fa-user-secret', 'initUserGroupList', '用户组管理', '1', 'MENU');
commit;


-- 用户用户组关系表
insert into test_report.sys_user_link_group(ID, GROUP_ID, UPDATE_TIME, UPDATE_USER_ID, USER_ID) values('1', '1', '2018-01-01 00:00:00.000000', '1', '1');
insert into test_report.sys_user_link_group(ID, GROUP_ID, UPDATE_TIME, UPDATE_USER_ID, USER_ID) values('2', '2', '2018-01-01 00:00:00.000000', '1', '2');
commit;



-- 用户组与角色之间的关系
insert into test_report.sys_user_group_role (ID, GROUP_ID, ROLE_ID, UPDATE_TIME, UPDATE_USER_ID) values ('1', '1', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_user_group_role (ID, GROUP_ID, ROLE_ID, UPDATE_TIME, UPDATE_USER_ID) values ('2', '1', '2', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_user_group_role (ID, GROUP_ID, ROLE_ID, UPDATE_TIME, UPDATE_USER_ID) values ('3', '1', '3', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_user_group_role (ID, GROUP_ID, ROLE_ID, UPDATE_TIME, UPDATE_USER_ID) values ('4', '1', '4', '2018-01-01 00:00:00.000000', '1');
commit;


insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('1', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('2', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('3', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('4', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('5', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('6', '1', '2018-01-01 00:00:00.000000', '1');
insert into test_report.sys_role_resource (RESOURCE_ID, ROLE_ID, UPDATE_TIME, USER_ID) values ('7', '1', '2018-01-01 00:00:00.000000', '1');
commit;


use test_report;
create or replace view v_user_role_resources as
  select u.id as user_id,
         r.id as resource_id,
         r.resource_code as resource_code,
         r.resource_name as resource_name,
         r.resource_css as resource_css,
         r.resource_link as resource_link,
         r.resource_status as resource_status,
         r.resource_type as resource_type
    from sys_user u, 
         sys_role_resource rr,
         sys_resource r 
    where
         u.role_id = rr.role_id 
         and rr.resource_id = r.id
         and r.resource_status=1;

commit;