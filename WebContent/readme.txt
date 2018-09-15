系统后台
index.html s.html Section.html 
login.html

WebRoot/ css、images、img、javascript、js、thirdparty、uploadify等为颜色、脚本、组件等


dataDictionary系统基础数据管理模块
	资讯标签管理
	签到管理
	资讯类别管理
一些基础数据，比如下拉框框显示的数据等


operator权限管理
操作员/用户 管理
Permission
角色管理 涉及三张表（t_user/t_role/t_role_menu）菜单树表(t_menu 通过menuid/parmenuid实现)

操作员/用户user_id/user_role 角色role_id/role_status/role_name 角色菜单menu_id/role_id/enable
1用户--1角色   1角色--N菜单
添加一个用户的时候，指定该用户的角色，角色id赋值给user_role，所以是先有角色，后有用户

角色管理
添加一个角色，指定该角色对应的菜单，先有菜单，后有角色，添加一个角色时，如果对应多个菜单，则new保存多个角色菜单对象


wx_user微信用户管理
群众管理、专家管理、群组管理

information资讯管理

reportForms报表管理


DTO作前台服务工作，从数据库查询时可以单作传递对象，如ibaties里查询时，通过 as 把查询出来的数据对应到dto中组成对象