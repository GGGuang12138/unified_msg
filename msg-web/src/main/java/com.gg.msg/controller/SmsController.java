package com.gg.msg.controller;

import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.handler.SmsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: GG
 * @date: 2022/2/15 12:27 上午
 */
@RestController
public class SmsController {

    @Autowired
    private SmsHandler smsHandler;

    /**
     * 测试发送短信
     * @param phone 手机号
     * @return
     */
    @GetMapping("/sendSms")
    public boolean sendSms(String phone,String content,Long messageTemplateId ) {

        TaskInfo taskInfo = TaskInfo.builder().receiver(new HashSet<>(Arrays.asList(phone)))
                .content(content).messageTemplateId(messageTemplateId).build();
        return smsHandler.doHandler(taskInfo);


    }
}
