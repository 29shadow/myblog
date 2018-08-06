package com.gtzhou.blog.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:java8 stream操作
 * User: ZGW
 * Date: 2018/7/4
 */
public class TestJava8 {

    //
    @Test
    public void testStream(){
        //
        List<String> list = new ArrayList<>();
        list.add("ew");
        list.add("wedrT");
        list.add("cdTgg");
        //list.stream().map(String::toUpperCase).forEach(System.out::println);

        //
        List<Integer> status = new ArrayList<>();
        status.add(1);
        status.add(2);
        status.add(3);
        //status.stream().map(TestJava8::paseStatus2Str).forEach(System.out::println);
        //status.stream().mapToDouble(x -> x).forEach(System.out::println);
        //status.stream().mapToLong(x -> x).forEach(System.out::println);

        status.stream().flatMap(x -> {
            if (x==2) {
                return list.stream().skip(x);
            }else {
                return null;
            }
        }).forEach(System.out::println);

    }

    private static String paseStatus2Str(Integer status){
        if (Objects.equals(status,1)){
            return "催收中";
        } else if (Objects.equals(status,2)){
            return "催收完成";
        } else {
            return "未知";
        }
    }


    @Test
    public void testDate(){
        System.out.println(LocalDate.now().atStartOfDay());
    }

    @Test
    public void testEqu(){
        int x = 1;
        Integer y = 1;
        System.out.println(Objects.equals(x,y));
    }
}
