package com.ssj.service;

import com.ssj.spring.SsjApplicationContext;

public class Test {

    public static void main(String[] args) {
        // 创建自己的spring容器 getsetbean
        SsjApplicationContext ssjApplicationContext = new SsjApplicationContext(AppConfig.class);
        // UserService userService = (UserService)
        // ssjApplicationContext.getBean("userService");
        // userService.equals(userService);

        // System.out.println(ssjApplicationContext.getBean("userService"));
        // System.out.println(ssjApplicationContext.getBean("userService"));
        // System.out.println(ssjApplicationContext.getBean("userService"));
        // System.out.println(ssjApplicationContext.getBean("userService"));
        // System.out.println(ssjApplicationContext.getBean("userService"));
        // System.out.println(ssjApplicationContext.getBean("orderService"));
        UserService userService = (UserService) ssjApplicationContext.getBean("userService");
        userService.test();
    }
}
