package com.gg.msg.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.handler.SmsHandler;
import com.gg.msg.service.api.domain.MessageParam;
import com.gg.msg.service.api.domain.SendRequest;
import com.gg.msg.service.api.domain.SendResponse;
import com.gg.msg.service.api.enums.BusinessCode;
import com.gg.msg.service.api.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: GG
 * @date: 2022/2/15 12:27 上午
 */
@RestController
@Slf4j
public class SmsController {

    @Autowired
    private SendService sendService;

    @ApolloConfig("unifiedMsg")
    private Config config;

    @RequestMapping("/apollo")
    public String testApollo() {
        return config.getProperty("a", "c");
    }

    @GetMapping("/sendSmsV2")
    public SendResponse sendSmsV2(String phone) {

        /**
         *
         * messageTemplate Id 为1 的模板内容
         * {"auditStatus":10,
         * "auditor":"yyyyyyz",
         * "created":1636978066,
         * "creator":"yyyyc",
         * "deduplicationTime":1,
         * "expectPushTime":"0",
         * "flowId":"yyyy",
         * "id":1,
         * "idType":20,
         * "isDeleted":0,
         * "isNightShield":0,
         * "msgContent":"{\"content\":\"{$contentValue}\"}",
         * "msgStatus":10,
         * "msgType":10,
         * "name":"test短信",
         * "proposer":"yyyy22",
         * "sendAccount":66,
         * "sendChannel":30,
         * "team":"yyyt",
         * "templateType":10,
         * "updated":1636978066,
         * "updator":"yyyyu"}
         *
         */

        phone = "15625501056";
        // 文案参数
        Map<String, String> variables = new HashMap<>();
        variables.put("contentValue", "6666");

        MessageParam messageParam = new MessageParam().setReceiver(phone).setVariables(variables);

        // ID为1的消息模板
        SendRequest sendRequest = new SendRequest().setCode(BusinessCode.COMMON_SEND.getCode())
                .setMessageTemplateId(1L)
                .setMessageParam(messageParam);

        SendResponse response = sendService.send(sendRequest);

        return response;
    }
}
