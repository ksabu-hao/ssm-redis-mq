package com.ksabu.mq.common.mq.producer;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.ksabu.mq.common.config.ConfigCenter;
import com.ksabu.mq.common.obj.MessageAggregateVo;
import com.ksabu.mq.common.obj.MqTopicBindHandlerEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.ksabu.mq.common.constant.CommonConstants.STRING_ONE;

/**
 * 消息发送实现
 * @author xiaohao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageQueueProducerImpl implements IMessageQueueProducer<Object> {

    private final StringRedisTemplate stringRedisTemplate;
    private final ConfigCenter configCenter;

    /**
     * 发送消息
     * @author xiaohao
     */
    @Override
    public void sendMessage(MessageAggregateVo<Object> messageAggregateVo) {

        checkParam(messageAggregateVo);

        // 构建基础信息
        String messageId = IdUtil.fastSimpleUUID();
        messageAggregateVo.setMessageId(messageId);
        messageAggregateVo.setSendName(configCenter.getSpringApplicationName());
        messageAggregateVo.setReceiveName(messageAggregateVo.getMqTopicBindHandlerEnum().getConsumerServiceGroup().toString());

        // 保存当前mq消息记录
        addMqRecord(messageAggregateVo);

        long start = System.currentTimeMillis();
        toMessage(messageAggregateVo);

        long consumerTime = System.currentTimeMillis() - start;
        log.info("发送消息至mq，耗时【{}】", consumerTime);
    }

    /**
     * 新增消息记录
     * @author xiaohao
     */
    private void addMqRecord(MessageAggregateVo<Object> messageAggregateVo) {
        // TODO: feign、http、mq新增mq记录
    }

    /**
     * 发送消息
     * @author xiaohao
     */
    private void toMessage(MessageAggregateVo<Object> messageAggregateVo) {

        String mqMessage = JSON.toJSONString(messageAggregateVo);

        if (!STRING_ONE.equals(configCenter.getSendMessageToMqFlag())) {
            log.error("【{}】服务已关闭发送消息至MQ,请排查关闭原因", configCenter.getSpringApplicationName());
            return;
        }

        log.info("发送消息至【{}】,消息【{}】", messageAggregateVo.getMqTopicBindHandlerEnum().getTopic(), mqMessage);
        stringRedisTemplate.convertAndSend(messageAggregateVo.getMqTopicBindHandlerEnum().getTopic(), mqMessage);
    }

    /**
     * 参数校验
     * @author xiaohao
     */
    private void checkParam(MessageAggregateVo<Object> messageAggregateVo) {

        MqTopicBindHandlerEnum mqTopicBindHandlerEnum = messageAggregateVo.getMqTopicBindHandlerEnum();


        if (mqTopicBindHandlerEnum == null || StringUtils.isEmpty(mqTopicBindHandlerEnum.getTopic()) || messageAggregateVo == null) {
            throw new RuntimeException("参数异常");
        }
    }
}
