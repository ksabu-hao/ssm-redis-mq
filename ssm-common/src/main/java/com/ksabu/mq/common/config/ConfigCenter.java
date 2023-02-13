package com.ksabu.mq.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置中心
 * @author xiaohao
 */
@Data
@Configuration
public class ConfigCenter {

    /**
     * 服务引入mq对应的name
     */
    @Value("${system.mq.app.name:ssm-example}")
    private String systemMqAppName;

    /**
     * 服务名称
     */
    @Value("${spring.application.name:ssm-example}")
    private String springApplicationName;

    /**
     * 发送消息到mq标志 1：默认发送 0：不发送
     */
    @Value("${send.message.to.mq:1}")
    private String sendMessageToMqFlag;

}
