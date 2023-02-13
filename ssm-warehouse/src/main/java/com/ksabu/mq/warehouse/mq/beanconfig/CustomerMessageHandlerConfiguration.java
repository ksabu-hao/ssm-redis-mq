package com.ksabu.mq.warehouse.mq.beanconfig;

import com.ksabu.mq.warehouse.mq.consumer.ProductWarehouseUpdateConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import static com.ksabu.mq.common.constant.CommonConstants.ACCEPT;

/**
 * 自定义topic-handler绑定
 * @author xiaohao
 */
@Slf4j
@Component
public class CustomerMessageHandlerConfiguration {

    /**
     * 下订单成功库存变化事件
     * @author xiaohao
     */
    @Bean
    public MessageListenerAdapter productWarehouseUpdateHandler(ProductWarehouseUpdateConsumer handler) {
        log.info("初始化下单成功库存变化事件自定义处理器【{}】...", handler.getClass().getName());
        return new MessageListenerAdapter(handler, ACCEPT);
    }
}
