package com.zhong.demo3;

import com.zhong.demo1.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamList {


    public static void main(String[] args) {

        // 1. 需求：查找集合中姓张并且名字长度为3的人员
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("张强");
        list.add("张三丰");


        demo1(list);

        demo2(list);
    }

    private static void demo2(List<String> list) {
        Stream<String> stream = list.stream();
        stream.filter(name -> name.startsWith("张"))
                .filter(name -> name.length() == 3)
                .forEach(name -> System.out.println(name));

        // todo  对容器的增强，  lsit、map、数组 数据的处理效率  代码可读性强。
        // todo  所有的实现了collection的类都可以使用stream
        // todo  stream 是基于函数式编成发展起来的
        // todo  只关注实现本身
        // todo  中间操作和终止操作  可以0个和多个中间  只有一个终止操作
        /*
         * 中间操作：
         *         filter(过滤)
         *         distinct(去重)
         *         sorted(排序)
         *         limit、skip(截取)
         *         map、flatMap(转换) 生成一个新的集合
         *         peek(其他)  打印流经的每个元素的状态 调试用处
         *
         *
         *
         * 终止操作：
         *          forEach(循环)
         *          min、max、count、average(计算)
         *          anyMatch、allMatch、noneMatch、findFirst、findAny(匹配)
         *          reduce(汇聚)
         *          toArray、collect(收集器)
         */
        // todo  每一次中间操作都是一个新的Stream
        // todo  延迟处理(返回Stream的时候，代码逻辑永远都不会执行) 只有在终止操作的时候才会处理   也就是说中间操作可以并行





        stream.parallel();
        list.parallelStream();
        // todo  提供串行和并行两种模式  ForkJoin  分而治之框架  是java并发包下非常重要的一个框架   将大任务拆分成小任务 小任务计算完将结果合并
        // todo  并不是并行流强大 就要用它   多线程   会出现死锁等问题   必须保证线程安全    适用于 多CPU 数据量大




        // filter(过滤)
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        integers.stream().filter(i -> i % 2 == 0).forEach(System.out::println);

        // 取第一条数据 如果没有则返回null
        List<User> users = new ArrayList<>();
        User user = users.stream().findFirst().orElse(null);

        // 帮助调试
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        // 取第几条数据返回
        List<User> collect1 = users.stream().limit(2).collect(Collectors.toList());

        // 生成一个新集合
        List<String> collect2 = users.stream().map(u -> user.getName()).collect(Collectors.toList());

        // 对分数求和
        users.stream().map(u->u.getScore()).reduce(BigDecimal.ZERO,BigDecimal::add);

        // mapToInt 求和
        List<Integer> integers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        int sum = integers2.stream().filter(i -> i % 2 == 0)
                .mapToInt(i -> i).sum();

        // 求集合中最大值
        List<Integer> integers3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Integer max = integers3.stream().max((a, b) -> a - b).get();

        // 求集合中最小值
        List<Integer> integers4 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Integer min = integers4.stream().min((a, b) -> a - b).get();

        // 获取第一个值
        Integer findAny = integers4.stream()
                .filter((x) -> x % 2 == 0).findAny().get();
        Integer findFirst = integers4.stream()
                .filter((x) -> x % 2 == 0).findFirst().get();

        // 结果返回Stream的时候不会执行 代码逻辑
        Stream<Integer> integerStream = integers4.stream().filter(i -> {
            System.out.println("运行代码");
            return i % 2 == 0;
        });

        // 排序（自然排序）
        Arrays.asList("java","c#","python","scala")
                .stream().sorted().forEach(System.out::println);

        // 长度排序
        Arrays.asList("java","c#","python","scala")
                .stream().sorted((a,b)->a.length()-b.length())
                .forEach(System.out::println);

        //返回 对象集合以name一升序排序
        users.stream().sorted(Comparator.comparing(User::getName));
        //先以属性name升序,升序结果进行属性name降序,再进行属性age降序
        users.stream().sorted(Comparator.comparing(User::getName).reversed().thenComparing(User::getAge,Comparator.reverseOrder()));
        //先以属性name降序,再进行属性age降序
        users.stream().sorted(Comparator.comparing(User::getName,Comparator.reverseOrder()).thenComparing(User::getAge,Comparator.reverseOrder()));


        // 将集合中的元素进行过滤 同时返回一个集合对象
        List<Integer> list1 = Arrays.asList(1,2,3,4,5,6,7,7,8,8);
        final List<Integer> collect = list1.stream().filter(x -> x % 2 == 0)
                .collect(Collectors.toList());

        // 去重操作
        list.stream().distinct().forEach(System.out::println);
        list.stream().collect(Collectors.toSet()).forEach(System.out::println);

        // 打印20-38的这样的集合数据
        Stream.iterate(1,x->x+1).limit(50).skip(20).limit(10)
                .forEach(System.out::println);

        // 求字符串中的数的和
        String str = "11,22,33,44,55";
        Stream.of(str.split(",")).mapToInt(x -> Integer.valueOf(max)).sum();
        Stream.of(str.split(",")).mapToInt(Integer::valueOf).sum();


        // 将str中的每一个值都打印出来，同时算出最终的求和结果
        Stream.of(str.split(",")).peek(System.out::println)
                .mapToInt(Integer::valueOf)
                .sum();

        // 判断集合中数据是否匹配，全都匹配返回ture
        list1.stream().allMatch(x->x%2==0);

        // 判断集合中数据是否匹配，任何一个匹配返回ture
        list1.stream().anyMatch(x->x%2==0);

        // 将两个Stream集合合并
        Stream.concat(list.stream(), list1.stream()).forEach(System.out::println);

        // 生成拼接字符串
        List<String>ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");
        String joinResult = ids.stream().collect(Collectors.joining(",")); 
        System.out.println("拼接后：" + joinResult);

        // 数据批量数学运算
        List<Integer> ids1 = Arrays.asList(10, 20, 30, 40, 50);
        // 计算平均值
        Double average = ids1.stream().collect(Collectors.averagingInt(value -> value));
        System.out.println("平均值：" + average);    

    }

    private static void demo1(List<String> list) {
        //对list集合中的元素进行过滤,只要以张开头的元素,存储到一个新的集合中
        List<String> listA = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith("张")) {
                listA.add(s);
            }
        }
        //对listA集合进行过滤,只要姓名长度为3的人,存储到一个新集合中
        List<String> listB = new ArrayList<>();
        for (String s : listA) {
            if (s.length() == 3) {
                listB.add(s);
            }
        }
    }

}
