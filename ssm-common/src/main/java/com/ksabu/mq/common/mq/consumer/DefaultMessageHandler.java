package com.ksabu.mq.common.mq.consumer;

import com.ksabu.mq.common.obj.MessageAggregateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 默认消息处理-模板
 * @author xiaohao
 */
@Slf4j
@Component
public class DefaultMessageHandler extends AbstractMessageConsumer<String> {

    /**
     * 默认处理器消费消息
     * @author xiaohao
     */
    @Override
    public MessageAggregateVo<String> consumer(MessageAggregateVo<String> messageAggregateVo) {
        log.info("默认处理器消费消息【{}】", messageAggregateVo);
        return messageAggregateVo;
    }

}
