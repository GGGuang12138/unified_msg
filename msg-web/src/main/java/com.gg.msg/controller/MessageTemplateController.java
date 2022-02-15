package com.gg.msg.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.gg.msg.support.dao.MessageTemplateDao;
import com.gg.msg.support.domain.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GG
 * @date: 2022/2/15 12:26 下午
 */
@RestController
public class MessageTemplateController {

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    /**
     * test insert
     */
    @GetMapping("/insert")
    public String insert() {

        MessageTemplate messageTemplate = MessageTemplate.builder()
                .name("test短信")
                .auditStatus(10)
                .flowId("yyyy")
                .msgStatus(10)
                .idType(10)
                .sendChannel(10)
                .templateType(10)
                .msgType(10)
                .expectPushTime("0")
                .msgContent("3333333m")
                .sendAccount(66)
                .creator("yyyyc")
                .updator("yyyyu")
                .team("yyyt")
                .proposer("yyyy22")
                .auditor("yyyyyyz")
                .isDeleted(0)
                .created(Math.toIntExact(DateUtil.currentSeconds()))
                .updated(Math.toIntExact(DateUtil.currentSeconds()))
                .deduplicationTime(1)
                .isNightShield(0)
                .build();

        MessageTemplate info = messageTemplateDao.save(messageTemplate);

        return JSON.toJSONString(info);

    }
}
