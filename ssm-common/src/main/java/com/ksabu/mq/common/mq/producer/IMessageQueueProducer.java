package com.ksabu.mq.common.mq.producer;

import com.ksabu.mq.common.obj.MessageAggregateVo;

/**
 * mq消息发送入口
 * @author xiaohao
 */
public interface IMessageQueueProducer<T> {

    /**
     * 发送消息至Mq
     * @param message 泛型消息
     * @author xiaohao
     */
    void sendMessage(MessageAggregateVo<T> message);

}
