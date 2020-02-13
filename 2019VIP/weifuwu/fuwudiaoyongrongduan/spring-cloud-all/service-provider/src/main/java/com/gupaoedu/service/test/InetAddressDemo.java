package com.gupaoedu.service.test;

import java.net.InetAddress;
import java.util.Random;
import java.util.stream.Stream;

public class InetAddressDemo {

    public static void main(String[] args) throws Exception {

        Stream.of(InetAddress.getAllByName("www.baidu.com"))
                .forEach(System.out::println);
//        www.baidu.com/180.101.49.12
//        www.baidu.com/180.101.49.11

        Random random = new Random();

        int size = 2;
        // 随机
        random.nextInt(size+1); // [1,2]
        // 轮训（环状） Ring

        // 1,2-> 1

        // 11 or 12 (轮训、随机)
        // 11(粘性)
    }
}
