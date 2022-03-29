package com.gg.msg.handler.dedupliction.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.gg.msg.domain.TaskInfo;
import org.springframework.stereotype.Service;

/**
 * 内容去重服务
 * @author: GG
 * @date: 2022/3/13 10:22 下午
 */
@Service
public class ContentDeduplicationService extends AbstractDeduplicationService{

    @Override
    protected String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return DigestUtil.md5Hex(taskInfo.getMessageTemplateId() + receiver
                + JSON.toJSONString(taskInfo.getContentModel()));
    }
}
