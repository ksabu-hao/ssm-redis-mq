package com.ksabu.mq.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 库存启动器
 * @author xiaohao
 */
@SpringBootApplication(scanBasePackages = "com.ksabu.mq.*")
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

}
