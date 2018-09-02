package com.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>ClassName: RedisClient</p>
 * Description: redis 客户端封装 <br/>
 * @date 2018年9月2日 下午4:39:48 
 * @author wangrenzong@cloudwalk.cn
 * @version 1.0
 * @since JDK 1.7
 */ 
public class RedisClient{
	 /** 
     * 日志记录 
     */  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /** 
     * redis 连接池 
     */  
    private JedisPool pool;  
      
    public void setPool(JedisPool pool) {  
        this.pool = pool;  
    }  
    /** 
     * 获取jedis  
     * @return 
     */  
    public Jedis getResource(){  
        Jedis jedis =null;  
        try {  
            jedis =pool.getResource();  
        } catch (Exception e) {  
        	logger.info("can't  get  the redis resource");  
        }  
        return jedis;  
    }  
    /** 
     * 关闭连接 
     * @param jedis 
     */  
    public void disconnect(Jedis jedis){  
        jedis.disconnect();  
    }  
    /** 
     * 将jedis 返还连接池 
     * @param jedis 
     */  
    public void returnResource(Jedis jedis){  
        if(null != jedis){  
            try {  
                pool.returnResource(jedis);  
            } catch (Exception e) {  
            	logger.info("can't return jedis to jedisPool");  
            }  
        }  
    }  
    /** 
     * 无法返还jedispool，释放jedis客户端对象， 
     * @param jedis 
     */  
    public void brokenResource(Jedis jedis){  
        if (jedis!=null) {  
            try {  
                pool.returnBrokenResource(jedis);  
            } catch (Exception e) {  
            	logger.info("can't release jedis Object");  
            }  
        }  
    }  
}  
