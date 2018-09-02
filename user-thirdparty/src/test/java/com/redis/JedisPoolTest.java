package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {
	public static void main(String[] args) {
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(10);
		
		JedisPool jedisPool=null;
		Jedis jedis=null;
		try{
			jedisPool=new JedisPool(config, "127.0.0.1", 6379);
			jedis=jedisPool.getResource();
//			jedis.set("name", "11111");
			String value=jedis.get("name");
			jedis.del("name");
			System.out.println(value);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
			if(jedisPool!=null){
				jedisPool.close();
			}
		}
	}
}
