package com.user.mapper;

import com.user.entity.User;

/**
 * 用户Mapper接口
 * @author Administrator
 *
 */
public interface UserMapper {

	User login(User user);
	
	User findById(String id);
}
