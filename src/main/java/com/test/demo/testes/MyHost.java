package com.test.demo.testes;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyHost {
    public static void main(String args[]) {
        InetAddress ip = null;

        try {
            ip = InetAddress.getByName("127.0.0.1");// 修改为指定的主机名称
            System.out.println(ip.toString());
            System.out.println("主机名：" + ip.getHostName());
            System.out.println("主机IP地址：" + ip.getHostAddress());
            System.out.println("本机IP地址："+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
