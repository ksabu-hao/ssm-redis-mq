package com.ksabu.mq.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ksabu.mq.common.obj.MessageAggregateVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.ksabu.mq.common.constant.CommonConstants.FAIL;
import static com.ksabu.mq.common.constant.CommonConstants.SUCCESS;

/**
 * mq消息消费成功更新状态切面类
 * @author xiaohao
 */
@Slf4j
@Aspect
@Component
public class MqConsumerAspect {

    /**
     * 满足com.ksabu.mq.**.mq.consumer.*.consumer()方法
     * 环切
     * @param point 切点
     */
    @Around("execution(* com.ksabu.mq..*.mq.consumer.*.consumer(..))")
    public Object around(ProceedingJoinPoint point) {

        MessageAggregateVo<String> messageAggregate = new MessageAggregateVo<>();
        String consumerStatus = SUCCESS;
        try {
            Object response = point.proceed();

            messageAggregate = JSONObject.parseObject(JSON.toJSONString(response), new TypeReference<MessageAggregateVo<String>>() {});
            log.info("切面获取到消费者返回体【{}】【{}】【{}】", messageAggregate.getMessageId(), messageAggregate.getBusinessMessageId(), messageAggregate);

            return response;
        } catch (Exception ex) {
            // TODO 模拟自定义系统异常
            log.error("异常", ex);
            consumerStatus = FAIL;
            throw new RuntimeException("出现自定义异常", ex);
        } catch (Throwable ex) {
            consumerStatus = FAIL;
            throw new RuntimeException("切面修改消息状态出现异常");
        } finally {
            // 消费状态
            if (null != messageAggregate.getSaveRecordFlag() && messageAggregate.getSaveRecordFlag()) {
                log.info("将消息id【{}】状态调整为【{}】", messageAggregate.getMessageId(), consumerStatus);
                messageAggregate.setConsumerStatus(consumerStatus);
                updateConsumerStatus(messageAggregate);
            }
        }
    }

    /**
     * 修改状态
     * @author xiaohao
     */
    private void updateConsumerStatus(MessageAggregateVo<String> messageAggregate) {
        // 接口或feign修改mq记录状态
    }

}
