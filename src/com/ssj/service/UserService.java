package com.ssj.service;

import com.ssj.spring.Autowired;
import com.ssj.spring.Component;
import com.ssj.spring.Scope;

@Component("userService")
// @Scope("prototype")
@Scope("")
public class UserService {

    // @Autowired 测试
    @Autowired // spring 会自动给这个字段提供值
    private OrderService orderService;

    public void test() {
        System.out.println("测试orderService的值: " + orderService);
    }

}
