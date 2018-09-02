package com.redis;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {
		Jedis jedis=new Jedis("127.0.0.1", 6379);
		jedis.set("name", "1223jedis测试1");
		String value=jedis.get("name");
		System.out.println(value);
		jedis.close();
	}
}
