package com.gg.msg.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gg.msg.enums.ResultStatus;
import com.gg.msg.service.api.domain.MessageParam;
import com.gg.msg.service.api.impl.domain.SendTaskModel;
import com.gg.msg.support.pipeline.BusinessProcess;
import com.gg.msg.support.pipeline.ProcessContext;
import com.gg.msg.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: GG
 * @date: 2022/2/15 5:02 下午
 */
@Slf4j
public class PreParamCheckAction implements BusinessProcess {

    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        // 没有tempId 或者 msgParam为空
        if (messageTemplateId == null || CollUtil.isEmpty(messageParamList)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(ResultStatus.CLIENT_BAD_PARAMETERS));
        }
        // 过滤掉 receiver 为空的msgParam
        List<MessageParam> resultMessageParamList = messageParamList.stream().filter(messageParam -> StrUtil.isNotBlank(messageParam.getReceiver()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(resultMessageParamList)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(ResultStatus.CLIENT_BAD_PARAMETERS));
        }
        sendTaskModel.setMessageParamList(resultMessageParamList);
    }
}
