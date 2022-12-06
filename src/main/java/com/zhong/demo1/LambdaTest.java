package com.zhong.demo1;

public class LambdaTest {

    public static void main(String[] args) {
        // 我想使用这个接口，有几种方法？

        // 1 ?
        method1();

        // 2 ?
        method2();

        // lambda
        method3();
    }

    private static void method3() {

        Factory factory = () -> {
            return new User("李四", 18);
        };

        // todo 可以省略大括号{}（只有简单的表达式语句时）
        factory = () -> new User("王五", 20);
        User user3 = (User) factory.getObject();
        System.out.println(user3);

        // todo lambda表达式  他不关注子类  不关注匿名内部类  他只关注你的参数和函数本身  所以叫函数式编程。
    }

    private static void method1() {
        // 1. 子类实现接口  父类引用指向子类对象 这是一种多态的写法
        Factory factory = new SubClass();
        User user1 = (User) factory.getObject();
        System.out.println(user1);
    }

    private static void method2() {
        // 2. 匿名内部类
        Factory factory = new Factory() {
            @Override
            public Object getObject() {
                return new User("张三", 39);
            }
        };
        User user2 = (User) factory.getObject();
        System.out.println(user2);
    }
}
