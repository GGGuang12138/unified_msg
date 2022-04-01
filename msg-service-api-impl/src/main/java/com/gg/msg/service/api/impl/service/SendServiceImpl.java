package com.gg.msg.service.api.impl.service;

import cn.monitor4all.logRecord.annotation.OperationLog;
import com.gg.msg.service.api.domain.BatchSendRequest;
import com.gg.msg.service.api.domain.SendRequest;
import com.gg.msg.service.api.domain.SendResponse;
import com.gg.msg.service.api.impl.domain.SendTaskModel;
import com.gg.msg.service.api.service.SendService;
import com.gg.msg.support.pipeline.ProcessContext;
import com.gg.msg.support.pipeline.ProcessController;
import com.gg.msg.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author: GG
 * @date: 2022/2/15 4:12 下午
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    @Override
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    public SendResponse send(SendRequest sendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }

    @Override
    @OperationLog(bizType = "SendService#batchSend", bizId = "#batchSendRequest.messageTemplateId", msg = "#batchSendRequest")
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();
        ProcessContext context = ProcessContext.builder()
                .code(batchSendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();
        ProcessContext process = processController.process(context);
        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }
}
