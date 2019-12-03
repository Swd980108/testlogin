package com.test.demo.util;

public class RandomYzm {

    public static String randomNumeric(int n){
        double r=Math.random()*9+1;
        for(int i=0;i<n-1;i++){
            r*=10;
        }
        int random = (int)r;
        return random+"";
    }

    public static void main(String[] args) {
        System.out.println(randomNumeric(6));
    }
}
