package com.ksabu.mq.common.obj;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ksabu.mq.common.obj.MqConsumerServiceEnum.B_DEFAULT_SERVICE;
import static com.ksabu.mq.common.obj.MqConsumerServiceEnum.B_WAREHOUSE_SERVICE;

/**
 * 消息队列主题枚举
 * @author xiaohao
 */
@Getter
public enum MqTopicBindHandlerEnum {

    /**
     * 下订单成功同步库存系统
     */
    ORDER_SUCCESS_TO_WAREHOUSE("order_success_to_warehouse", "下订单成功同步库存系统", "productWarehouseUpdateHandler", Arrays.asList(B_WAREHOUSE_SERVICE)),
    DEFAULT_TOPIC("default", "默认", "defaultTopicHandler", Arrays.asList(B_DEFAULT_SERVICE))
    ;

    private final String topic;
    private final String name;
    private final String handler;
    private final List<MqConsumerServiceEnum> consumerServiceGroup;

    MqTopicBindHandlerEnum(String topic, String name, String handler, List<MqConsumerServiceEnum> consumerServiceGroup) {
        this.topic = topic;
        this.name = name;
        this.handler = handler;
        this.consumerServiceGroup = consumerServiceGroup;
    }

    /**
     * 通过消费服务配置获取需要监听的topic
     * @author xiaohao
     */
    public static List<MqTopicBindHandlerEnum> getEnumsByConsumerService(MqConsumerServiceEnum mqConsumerServiceEnum) {
        List<MqTopicBindHandlerEnum> list;
        list = Arrays.stream(MqTopicBindHandlerEnum.values())
                .filter(p -> p.getConsumerServiceGroup().contains(mqConsumerServiceEnum))
                .collect(Collectors.toList());

        if (list.size() == 0) {
            list.add(DEFAULT_TOPIC);
        }
        return list;
    }

    /**
     * 根据topicCode获取枚举
     * @author xiaohao
     */
    public static MqTopicBindHandlerEnum getEnumByTopicCode(String topic) {
        Optional<MqTopicBindHandlerEnum> first = Arrays.stream(MqTopicBindHandlerEnum.values()).filter(p -> p.getTopic().equals(topic)).findFirst();

        return first.orElse(DEFAULT_TOPIC);

    }

}
