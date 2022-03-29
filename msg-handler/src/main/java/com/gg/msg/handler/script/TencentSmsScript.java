package com.gg.msg.handler.script;

import com.gg.msg.handler.domain.SmsParam;
import com.gg.msg.support.dao.SmsRecordDao;
import com.gg.msg.support.domain.SmsRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 腾讯云短信
 * @author: GG
 * @date: 2022/2/13 9:51 下午
 */
@Service
@Slf4j
public class TencentSmsScript implements SmsScript{

    @Autowired
    private SmsRecordDao smsRecordDao;

    @Override
    public List<SmsRecord> send(SmsParam smsParam) throws Exception {
        if (smsParam.getPhones().size() == 0){
            log.info("发送腾讯云短信异常,目标手机号码不存在");
            throw new Exception("操作失败");
        }else{
            log.info("发送腾讯云短信,短信参数为：{}",smsParam);
        }
        //TODO：对接腾讯云短信（发送HTTP请求/接入对方的SDK）
        return null;
    }
}
