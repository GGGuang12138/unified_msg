package com.gg.msg.handler.pending;

import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.dedupliction.DeduplicationUnifiedService;
import com.gg.msg.handler.handler.Handler;
import com.gg.msg.handler.handler.HandlerHolder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务线程
 * @author: GG
 * @date: 2022/3/9 7:45 下午
 */
@Data
@Slf4j
@Accessors(chain = true)
public class TaskThread implements Runnable{

    private TaskInfo taskInfo;

    @Autowired
    private HandlerHolder handlerHolder;

    @Autowired
    private DeduplicationUnifiedService deduplicationUnifiedService;

    @Override
    public void run() {
        // 统一去重处理
        deduplicationUnifiedService.duplication(taskInfo);
        // 获取对应消息的处理器去执行
        Handler handler = handlerHolder.route(taskInfo.getSendChannel());
        handler.doHandler(taskInfo);
    }
}
