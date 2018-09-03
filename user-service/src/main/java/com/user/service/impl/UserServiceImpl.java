package com.user.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.user.entity.User;
import com.user.mapper.UserMapper;
import com.user.service.RedisService;
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
	
	@Resource(name="redisService")
	private RedisService redisService;
	
	public User login(User user) {
		return userMapper.login(user);
	}

	public User findById(String id) {
		String userJson = redisService.get("java");
		User user = null;
		if(StringUtils.isEmpty(userJson)){
			user=userMapper.findById(id);
			//不存在,设置
			if(user != null)
				redisService.set("java", user.getPassword());
		}else{
			user=userMapper.findById(id);
		}
		return user;
	}

}
