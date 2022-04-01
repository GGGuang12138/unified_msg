package com.gg.msg.handler.dedupliction.service;

import cn.hutool.core.collection.CollUtil;
import com.gg.msg.constant.BaseConstant;
import com.gg.msg.domain.AnchorInfo;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.handler.domain.DeduplicationParam;
import com.gg.msg.support.utils.LogUtils;
import com.gg.msg.support.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 去重服务的模版
 * @author: GG
 * @date: 2022/3/10 11:40 下午
 */
@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();
        // 根据receiver生成用于去重Key
        List<String> allDeduplicationKeys = deduplicationAllKey(taskInfo);

        // 已在redis的key、重新放进redis的key
        Map<String, String> inRedisValue = redisUtils.mGet(allDeduplicationKeys);
        Set<String> readyPutRedisKey = new HashSet<>(taskInfo.getReceiver().size());

        // 过滤掉符合条件的接收者，并确定重新放进redis的key
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());
        for (String receiver : taskInfo.getReceiver()){
            String key = deduplicationSingleKey(taskInfo, receiver);
            String value = inRedisValue.get(key);
            // 满足过滤条件的加入到filter里
            if (value != null && Integer.parseInt(value) >= param.getCountNum()){
                filterReceiver.add(receiver);
            }else {
                readyPutRedisKey.add(key);
            }
        }
        // 放进redis
        if (readyPutRedisKey.size() > 0){
            putInRedis(readyPutRedisKey,inRedisValue,param);
        }
        // 移除过滤掉的接收者
        if (CollUtil.isNotEmpty(filterReceiver)) {
            taskInfo.getReceiver().removeAll(filterReceiver);
            LogUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(filterReceiver).state(param.getAnchorState().getCode()).build());
        }
    }

    /**
     * 构建去重的Key
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    protected abstract String deduplicationSingleKey(TaskInfo taskInfo, String receiver);

    /**
     * 获取得到当前消息模板所有的去重Key
     *
     * @param taskInfo
     * @return
     */
    private List<String> deduplicationAllKey(TaskInfo taskInfo) {
        List<String> result = new ArrayList<>(taskInfo.getReceiver().size());
        for (String receiver : taskInfo.getReceiver()) {
            String key = deduplicationSingleKey(taskInfo, receiver);
            result.add(key);
        }
        return result;
    }

    private void putInRedis(Set<String> readyPutRedisKey,Map<String,String> inRedisValue,DeduplicationParam param){
        Map<String, String> keyValues = new HashMap<>(readyPutRedisKey.size());
        for (String key: readyPutRedisKey){
            if (inRedisValue.get(key) != null) {
                keyValues.put(key, String.valueOf(Integer.parseInt(inRedisValue.get(key)) + 1));
            } else {
                keyValues.put(key, String.valueOf(BaseConstant.TRUE));
            }
        }
        if (CollUtil.isNotEmpty(keyValues)) {
            redisUtils.pipelineSetEX(keyValues, param.getDeduplicationTime());
        }
    }

}
