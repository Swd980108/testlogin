package com.test.demo.test;

import com.test.demo.util.JwtUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("userid","1");
        map.put("username","swd");
        map.put("password","123456");
        String ip="127.0.0.1";
        String time = new SimpleDateFormat("yyyyMMdd HHmm").format(new Date());
        String encode = JwtUtil.encode("2019swd1108", map, ip+time);
        System.out.println(encode);
    }
}
