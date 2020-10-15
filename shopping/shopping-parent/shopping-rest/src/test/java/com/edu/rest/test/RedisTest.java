package com.edu.rest.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RedisTest {
    @Test
    public void test(){
        //连接本地的redis服务
        Jedis jedis=new Jedis("8.129.19.70",6379);
        System.out.println("连接成功");
        //
        jedis.set("runoobkey","www.runoob.com");
        System.out.println("redis存储的字符为："+jedis.get("runoobkey"));
        jedis.close();
    }

   @Test
    public void test2(){
        //连接本地的redis服务

        JedisPool pool=new JedisPool("8.129.19.70",6379);
        Jedis jedis=pool.getResource();
        System.out.println("连接成功");
        //
        jedis.set("key1","www.runoob.com");
        System.out.println("redis存储的字符为："+jedis.get("key1"));
        jedis.close();
    }

    @Test
    public void test3(){
        Set<HostAndPort> nodes=new HashSet<>() ;
        nodes.add(new HostAndPort("8.129.19.70",7001));
        nodes.add(new HostAndPort("8.129.19.70",7002));
        nodes.add(new HostAndPort("8.129.19.70",7003));
        nodes.add(new HostAndPort("8.129.19.70",7004));
        nodes.add(new HostAndPort("8.129.19.70",7005));
        nodes.add(new HostAndPort("8.129.19.70",7007));
        JedisCluster cluster=new JedisCluster(nodes,2000,100);
        cluster.set("cluster","cluster");
        String c=cluster.get("cluster");
        System.out.println(c);
        cluster.close();
    }
}
