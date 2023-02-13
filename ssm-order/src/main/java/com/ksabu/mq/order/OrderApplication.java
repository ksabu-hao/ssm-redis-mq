package com.ksabu.mq.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 订单启动器
 * @author xiaohao
 */
@SpringBootApplication(scanBasePackages = "com.ksabu.mq.**")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
