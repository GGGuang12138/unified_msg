package com.gg.msg.handler.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.dto.SmsContentModel;
import com.gg.msg.enums.ChannelType;
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
public class SmsHandler extends BaseHandler{

    @Autowired
    private SmsScript smsScript;
    @Autowired
    private SmsRecordDao smsRecordDao;

    public SmsHandler() {
        channelCode = ChannelType.SMS.getCode();
    }

    @Override
    public void handler(TaskInfo taskInfo) {
                SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(getSmsContent(taskInfo))
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
    }

    /**
     * 如果有输入链接，则把链接拼在文案后
     * <p>
     * PS: 这里可以考虑将链接 转 短链
     * PS: 如果是营销类的短信，需考虑拼接 回TD退订 之类的文案
     */
    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            return smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }
}
