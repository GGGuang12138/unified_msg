package com.gg.msg.handler.receiver;

import com.alibaba.fastjson.JSON;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.handler.SmsHandler;
import com.gg.msg.handler.pending.TaskPendingHolder;
import com.gg.msg.handler.pending.TaskThread;
import com.gg.msg.handler.utils.GroupIdMappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * 消费MQ消息
 * @author: GG
 * @date: 2022/3/4 10:03 下午
 */
@Slf4j
public class Receiver {

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Autowired
    private ApplicationContext applicationContext;

//    @KafkaListener(topics = {"unifiedMsg"})
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String groupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> lists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(lists.get(0));
            // 只关注自己的信息
            if (groupId.equals(messageGroupId)){
                for (TaskInfo info:lists){
                    TaskThread taskThread = applicationContext.getBean(TaskThread.class).setTaskInfo(info);
                    // 找到对应的线程池，并将线程任务交给池
                    ExecutorService executorService = taskPendingHolder.route(messageGroupId);
                    executorService.execute(taskThread);
                }
            }
        }

    }
}
