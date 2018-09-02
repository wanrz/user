package com.user.service;

import com.user.entity.User;

/**
 * 用户Service接口
 * @author Administrator
 *
 */
public interface UserService {

	User login(User user);
	
	User findById(String id);
}
