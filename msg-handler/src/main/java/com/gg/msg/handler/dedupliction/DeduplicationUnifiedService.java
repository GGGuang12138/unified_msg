package com.gg.msg.handler.dedupliction;

import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.dedupliction.service.ContentDeduplicationService;
import com.gg.msg.handler.domain.DeduplicationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 去重服务
 * @author: GG
 * @date: 2022/3/13 10:25 下午
 */
@Service
public class DeduplicationUnifiedService {

    @Autowired
    private ContentDeduplicationService contentDeduplicationService;

    public void duplication(TaskInfo taskInfo) {
        // 文案去重
        DeduplicationParam contentParams = DeduplicationParam.builder()
                .deduplicationTime(300L).countNum(1).taskInfo(taskInfo)
                .build();
        contentDeduplicationService.deduplication(contentParams);
    }
}
