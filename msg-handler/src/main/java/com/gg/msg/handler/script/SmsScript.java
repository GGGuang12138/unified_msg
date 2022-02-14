package com.gg.msg.handler.script;

import com.gg.msg.handler.domain.SmsParam;
import com.gg.msg.support.domain.SmsRecord;

import java.util.List;

/**
 * 短信接口
 * @author: GG
 * @date: 2022/2/13 2:44 下午
 */
public interface SmsScript {

    /**
     * 发送短信
     * @param smsParam
     * @return 渠道商接口返回值
     * @throws Exception
     */
    List<SmsRecord> send(SmsParam smsParam) throws Exception;

}
