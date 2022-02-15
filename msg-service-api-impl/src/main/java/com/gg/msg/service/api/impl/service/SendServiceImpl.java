package com.gg.msg.service.api.impl.service;

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

/**
 * @author: GG
 * @date: 2022/2/15 4:12 下午
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Arrays.asList(sendRequest.getMessageParam()))
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
    public SendResponse batchSend(SendRequest sendRequest) {
        return null;
    }
}
