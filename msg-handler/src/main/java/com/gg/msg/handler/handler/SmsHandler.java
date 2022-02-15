package com.gg.msg.handler.handler;

import cn.hutool.core.collection.CollUtil;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.domain.SmsParam;
import com.gg.msg.handler.script.SmsScript;
import com.gg.msg.support.dao.SmsRecordDao;
import com.gg.msg.support.domain.SmsRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息发送处理
 * @author: GG
 * @date: 2022/2/15 12:01 上午
 */
@Component
@Slf4j
public class SmsHandler implements Handler{

    @Autowired
    private SmsScript smsScript;
    @Autowired
    private SmsRecordDao smsRecordDao;

    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(taskInfo.getContent())
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .phones(taskInfo.getReceiver()).build();
        List<SmsRecord> recordList = new ArrayList<>();
        try {
            recordList = smsScript.send(smsParam);
        } catch (Exception exception) {
            log.error("发送失败");
        }
        if (!CollUtil.isEmpty(recordList)) {
            smsRecordDao.saveAll(recordList);
        }
        return false;
    }
}
