package com.ksabu.mq.common.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ksabu.mq.common.obj.MessageAggregateVo;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息处理器
 * @author xiaohao
 */
@Slf4j
public abstract class AbstractMessageConsumer<T> {

    /**
     * 接收消息并序列化传递到消费方法
     * @param message json消息
     * @param topic 主题
     */
    public final void accept(String message, String topic) {
        log.info("接收到topic【{}】的消息,抽象类进行消息反序列化", topic);
        MessageAggregateVo<T> messageAggregateVo = JSONObject.parseObject(message, new TypeReference<MessageAggregateVo<T>>() {});
        consumer(messageAggregateVo);
    }

    /**
     * 消费消息方法
     * @param messageAggregateVo 序列化后的消息
     * @return 返回序列化后的消息
     * @author xiaohao
     */
    protected abstract MessageAggregateVo<T> consumer(MessageAggregateVo<T> messageAggregateVo);

}
