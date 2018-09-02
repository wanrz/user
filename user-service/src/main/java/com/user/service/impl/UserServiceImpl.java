package com.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.service.UserService;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	public User login(User user) {
		return userMapper.login(user);
	}

	public User findById(String id) {
		return userMapper.findById(id);
	}

}
