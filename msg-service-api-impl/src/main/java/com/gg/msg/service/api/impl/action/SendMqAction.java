package com.gg.msg.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gg.msg.enums.ResultStatus;
import com.gg.msg.service.api.impl.domain.SendTaskModel;
import com.gg.msg.support.pipeline.BusinessProcess;
import com.gg.msg.support.pipeline.ProcessContext;
import com.gg.msg.vo.BasicResultVO;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author: GG
 * @date: 2022/2/15 5:02 下午
 */
@Slf4j
public class SendMqAction implements BusinessProcess {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${msg.business.topic.name}")
    private String topicName;

    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();
        try {
            kafkaTemplate.send(topicName, JSON.toJSONString(sendTaskModel.getTaskInfo()),
                    new SerializerFeature[] {SerializerFeature.WriteClassName});
        } catch (Exception exception) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(ResultStatus.SERVICE_ERROR));
            log.error("send kafka fail! e:{},params:{}", Throwables.getStackTraceAsString(exception)
            ,JSON.toJSONString(CollUtil.getFirst(sendTaskModel.getTaskInfo().listIterator())));
        }


    }
}
