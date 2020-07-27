package com.pointlion.mvc.admin.sys.user;

import java.util.List;

import com.pointlion.mvc.common.model.SysUser;


public class SysUserService {
	public static final SysUserService me = new SysUserService();
	
	/***
	 * 获取所有用户
	 * @return
	 */
	public List<SysUser> getAllUser(){
		return SysUser.dao.find("select * from sys_user");
	}


}