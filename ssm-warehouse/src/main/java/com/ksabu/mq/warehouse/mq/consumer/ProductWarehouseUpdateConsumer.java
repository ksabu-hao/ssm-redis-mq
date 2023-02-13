package com.ksabu.mq.warehouse.mq.consumer;

import com.ksabu.mq.common.mq.consumer.AbstractMessageConsumer;
import com.ksabu.mq.common.obj.MessageAggregateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 产品库存修改消费者
 * @author xiaohao
 */
@Slf4j
@Service
public class ProductWarehouseUpdateConsumer extends AbstractMessageConsumer<String> {

    /**
     * 产品库存修改消费者
     * @author xiaohao
     */
    @Override
    public MessageAggregateVo<String> consumer(MessageAggregateVo<String> messageAggregateVo) {
        log.info("消费消息成功【{}】", messageAggregateVo);
        return messageAggregateVo;
    }
}
