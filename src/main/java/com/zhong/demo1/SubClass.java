package com.zhong.demo1;

// 实现类
public class SubClass implements Factory {
    @Override
    public Object getObject() {
        return new User();
    }
}
