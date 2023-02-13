package com.ksabu.mq.common.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息聚合vo
 * @author xiaohao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageAggregateVo<T> implements Serializable {

    /**
     业务消息唯一id
     */
    private String businessMessageId;
    /**
     业务消息体
     */
    private T data;
    /**
     是否保存消息记录
     */
    private Boolean saveRecordFlag;
    /**
     发送方
     */
    private String sendName;
    /**
     接收方
     */
    private String receiveName;
    /**
     topic相关
     */
    private MqTopicBindHandlerEnum mqTopicBindHandlerEnum;
    /**
     当前消息唯一id（非业务）
     */
    private String messageId;
    /**
     消费状态
     */
    private String consumerStatus;
    /**
     补偿消息标志
     */
    private Boolean compensatorFlag = Boolean.FALSE;
}
