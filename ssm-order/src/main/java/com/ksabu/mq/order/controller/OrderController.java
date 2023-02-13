package com.ksabu.mq.order.controller;

import cn.hutool.core.util.IdUtil;
import com.ksabu.mq.common.mq.producer.IMessageQueueProducer;
import com.ksabu.mq.common.obj.MessageAggregateVo;
import com.ksabu.mq.common.obj.MqTopicBindHandlerEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单测试web
 * @author xiaohao
 */
@Slf4j
@RestController
@RequestMapping("/v1/order/")
@RequiredArgsConstructor
public class OrderController {

    private final IMessageQueueProducer<Object> messageQueueProducer;

    /**
     * 伪代码测试消息发送
     * @author xiaohao
     */
    @GetMapping(value = "test")
    public String liveView() {

        Map<String, String> orderInfo = new HashMap<>();
        orderInfo.put("orderId", "20230101");
        orderInfo.put("productId", "1");
        orderInfo.put("productCode", "phone");
        orderInfo.put("productName", "手机");
        orderInfo.put("desc", "下单成功转发库存系统");

        MessageAggregateVo<Object> vo = MessageAggregateVo.builder()
                .businessMessageId(IdUtil.fastUUID())
                .data(orderInfo)
                .saveRecordFlag(true)
                .mqTopicBindHandlerEnum(MqTopicBindHandlerEnum.ORDER_SUCCESS_TO_WAREHOUSE)
                .build();

        messageQueueProducer.sendMessage(vo);

        return "下单成功发送消息到库存系统";

    }

}
