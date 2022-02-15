package com.gg.msg.service.api.impl.domain;

import com.gg.msg.domain.TaskInfo;
import com.gg.msg.service.api.domain.MessageParam;
import com.gg.msg.support.pipeline.ProcessModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发送消息任务模型
 * @author: GG
 * @date: 2022/2/15 5:07 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskModel implements ProcessModel {

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的信息
     */
    private List<TaskInfo> taskInfo;
}
