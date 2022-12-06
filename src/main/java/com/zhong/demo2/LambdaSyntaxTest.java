package com.zhong.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * 前提：必须有一个函数式接口（@FunctionalInterface）
 */
public class LambdaSyntaxTest {
    public static void main(String[] args) {

        /* TODO 语法格式 ：
         * 1. (parameters) -> {statements};
         * parameters : 函数的参数列表
         * statements : 执行的语句
         * ->  :  指定参数去完成某个功能
         *
         * 2. (parameters) -> expression;
         * expression : 表达式
         * */


        // 1.Lambda表达式的基本格式
        IMathOperation mo0 = (int a, int b) -> {
            return a + b;
        };
        System.out.println(mo0.operation(1, 2));

        // 2.省略大括号（只有一个语句）
        IGreeting greeting = (String msg) -> System.out.println("hello " + msg);
        greeting.sayHello("LambdaBig");

        // 3.省略小括号（只有一个参数的时候）
        greeting = msg -> System.out.println("hello " + msg);
        greeting.sayHello("LambdaSmall");

        // 4.省略return（只有一个表达式）
        IMathOperation mo1 = (int a, int b) -> a + b;
        System.out.println(mo1.operation(3, 4));

        // 5.省略参数类型和大括号
        IMathOperation mo2 = (a, b) -> a - b;
        System.out.println(mo2.operation(6, 5));




        // 看了这些样例，那么到底什么样子的接口 可以用lambda表达式替代？
        // TODO 有且只有一个抽象方法的接口是函数式接口  函数式接口通过@FunctionalInterface标注。
        // 常见的函数式接口 Runnable、Callable、Comparator等
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "线程1已启动");
            }
        }).start();


        new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + "线程2已启动");
                }
        ).start();

    }
}
