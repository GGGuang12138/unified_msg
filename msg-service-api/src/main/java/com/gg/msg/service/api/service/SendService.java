package com.gg.msg.service.api.service;

import com.gg.msg.service.api.domain.BatchSendRequest;
import com.gg.msg.service.api.domain.SendRequest;
import com.gg.msg.service.api.domain.SendResponse;

/**
 * 发送消息接口
 * @author: GG
 * @date: 2022/2/15 4:12 下午
 */
public interface SendService {

    SendResponse send(SendRequest sendRequest);

    SendResponse batchSend(BatchSendRequest batchSendRequest);
}
