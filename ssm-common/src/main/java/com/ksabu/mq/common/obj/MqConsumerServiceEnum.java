package com.ksabu.mq.common.obj;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

/**
 * mq消费者服务
 * @author xiaohao
 */
@Getter
@ToString
public enum MqConsumerServiceEnum {

    /**
     业务-订单服务
     */
    B_ORDER_SERVICE( "b-" + "order", "业务-订单服务"),
    B_WAREHOUSE_SERVICE( "b-" + "warehouse", "业务-库存服务"),
    B_DEFAULT_SERVICE( "b-" + "default", "业务-默认服务");

    private final String code;
    private final String name;
    MqConsumerServiceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 通过code获取enum信息
     * @author xiaohao
     */
    public static MqConsumerServiceEnum getCodeByEnum(String code) {
        Optional<MqConsumerServiceEnum> option = Arrays.stream(MqConsumerServiceEnum.values()).filter(p -> p.getCode().equals(code)).findFirst();

        return option.orElse(B_DEFAULT_SERVICE);
    }

}
