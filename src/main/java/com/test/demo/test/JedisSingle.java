package com.test.demo.test;

import redis.clients.jedis.Jedis;

public class JedisSingle {

    // 单实例连接redis
    public static  void testJedisSingle() {

        Jedis jedis = new Jedis("123.56.146.70", 6379);
        jedis.set("name", "swd");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }

    public static void main(String[] args) {
        testJedisSingle();
    }


}
