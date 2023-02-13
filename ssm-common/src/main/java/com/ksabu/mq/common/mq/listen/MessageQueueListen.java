package com.ksabu.mq.common.mq.listen;

import com.alibaba.fastjson.JSON;
import com.ksabu.mq.common.config.ConfigCenter;
import com.ksabu.mq.common.mq.consumer.DefaultMessageHandler;
import com.ksabu.mq.common.obj.MqConsumerServiceEnum;
import com.ksabu.mq.common.obj.MqTopicBindHandlerEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.ksabu.mq.common.constant.CommonConstants.*;

/**
 redis-mq监听器
 @author xiaohao
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageQueueListen {

    private final ConfigCenter configCenter;

    /**
     * 注入topic监听器
     * 注意：只有当配置中心配置了开启mq才会注入当前监听器
     * @author xiaohao
     */
    @Bean
    @ConditionalOnProperty(name = ENABLE_MQ_KEY, havingValue = STRING_ONE)
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, Map<String, MessageListenerAdapter> map) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 监听对应消费者下的所有的topic
        log.info("配置中心配置的当前服务appName为【{}】", configCenter.getSystemMqAppName());
        List<MqTopicBindHandlerEnum> enums = MqTopicBindHandlerEnum.getEnumsByConsumerService(MqConsumerServiceEnum.getCodeByEnum(configCenter.getSystemMqAppName()));

        Iterator<MqTopicBindHandlerEnum> iterator = enums.iterator();
        while (iterator.hasNext()){
            MqTopicBindHandlerEnum p = iterator.next();
            log.info("topic【{}-{}】绑定的消息接收器为【{}】，所属消费组服务列表【{}】", p.getTopic() , p.getName(), p.getHandler(), p.getConsumerServiceGroup());
            try {
                container.addMessageListener(map.get(p.getHandler()), new PatternTopic(p.getTopic()));
            } catch (Exception e) {
                log.error("添加拦截器异常，可能是不存在这个bean【{}】", p.getHandler(), e);
                // 剔除无效topic监听
                log.error("剔除topic【{}】监听绑定信息", p.getTopic());
                iterator.remove();
            }
        }

        log.info("成功订阅topic列表信息【{}】", JSON.toJSONString(enums));
        return container;
    }

    /**
     * 默认处理器
     * @author xiaohao
     */
    @Bean
    @ConditionalOnProperty(name = ENABLE_MQ_KEY, havingValue = STRING_ONE)
    public MessageListenerAdapter defaultTopicHandler(DefaultMessageHandler defaultMessageHandler) {
        log.info("初始化redis消息队列默认处理器【{}】...", defaultMessageHandler.getClass().getName());
        return new MessageListenerAdapter(defaultMessageHandler, ACCEPT);
    }

}
