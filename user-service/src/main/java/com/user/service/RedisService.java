package com.user.service;

public interface RedisService {
	String get(String key) ;
 
	String set(String key, String value);
 
	String hget(String hkey, String key);
 
	long hset(String hkey, String key, String value);
}
