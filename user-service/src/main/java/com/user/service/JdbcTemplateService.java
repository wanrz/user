package com.user.service;

import java.util.List;

/**
 * <p>ClassName: JdbcTemplateService</p>
 * Description:jdbcTemplate服务<br/>
 * @date 2018年12月9日 上午10:15:01 
 * @author wangrenzong@cloudwalk.cn
 * @version 1.0
 * @since JDK 1.7
 */ 
public interface JdbcTemplateService {
	int update(String sql);
	
	List<String> query(String sql);
}
