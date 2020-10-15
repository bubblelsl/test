package com.edu.sso.dao.impl;

import com.edu.sso.dao.JedisClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class JedisClientDaoCluster implements JedisClientDao {
    @Autowired
    private JedisCluster redisClient;
    @Override
    public String set(String key, String value) {
        return redisClient.set(key,value);
    }

    @Override
    public String get(String key) {
        return redisClient.get(key);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        return redisClient.hset(hkey,key,value);
    }

    @Override
    public String hget(String hkey, String key) {
        return redisClient.hget(hkey,key);
    }

    @Override
    public long expire(String key, int seconds) {
        return redisClient.expire(key,seconds);
    }

    @Override
    public long ttl(String key) {
        return redisClient.ttl(key);
    }

    @Override
    public long incr(String key) {
        return redisClient.incr(key);
    }

    @Override
    public long del(String key) {
        return redisClient.del(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        return redisClient.hdel(hkey,key);
    }
}
