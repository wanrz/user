package com.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.user.service.JdbcTemplateService;

/**
 * <p>ClassName: JdbcTemplateImpl</p>
 * Description:jdbcTemplate服务实现类<br/>
 * @date 2018年12月9日 上午10:16:49 
 * @author wangrenzong@cloudwalk.cn
 * @version 1.0
 * @since JDK 1.7
 */ 
@Service("jdbcTemplateService")
public class JdbcTemplateImpl implements JdbcTemplateService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int update(String sql){
		return jdbcTemplate.update(sql);
	}
	
	@Override
	public List<String> query(String sql){
		return jdbcTemplate.queryForList(sql, String.class);
	}
	
	
}
