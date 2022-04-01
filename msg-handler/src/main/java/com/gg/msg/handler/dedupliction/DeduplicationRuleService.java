package com.gg.msg.handler.dedupliction;

import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.gg.msg.constant.BaseConstant;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.enums.AnchorState;
import com.gg.msg.handler.dedupliction.service.ContentDeduplicationService;
import com.gg.msg.handler.domain.DeduplicationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 去重服务
 * @author: GG
 * @date: 2022/3/13 10:25 下午
 */
@Service
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY = "deduplication";

    @ApolloConfig("unifiedMsg")
    private Config config;

    @Autowired
    private ContentDeduplicationService contentDeduplicationService;

    public void duplication(TaskInfo taskInfo) {

        // 配置样例：{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, BaseConstant.APOLLO_DEFAULT_VALUE_JSON_OBJECT);

        // todo 针对需要进行多个去重的情况
        // 获取参数，解析确定要执行的去重Service
        // for循环执行对应的Service

        // 文案去重
        DeduplicationParam deduplicationParam = JSONObject.parseObject(deduplicationConfig, DeduplicationParam.class);
        if (deduplicationParam == null) {
            return;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION);
        contentDeduplicationService.deduplication(deduplicationParam);
    }
}
