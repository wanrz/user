package com.java.user.dao;

import com.java.user.entity.User;

/**
 * 用户DAO接口
 * @author Administrator
 *
 */
public interface UserDao {

	User login(User user);
	
	User findById(String id);
}
