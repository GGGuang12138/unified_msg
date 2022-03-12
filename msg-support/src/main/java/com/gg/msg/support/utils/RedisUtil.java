package com.gg.msg.support.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis的二次封装
 * 所有方法都要try catch，确保发生异常只打印日志
 * @author: GG
 * @date: 2022/3/12 10:27 下午
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 批量获取放回map
     * @param keys
     * @return
     */
    public Map<String, String> mGet(List<String> keys) {
        Map<String,String> result = new HashMap<>(keys.size());
        //todo 为空是否返回null
        List<String> value = redisTemplate.opsForValue().multiGet(keys);
        try {
            if (CollUtil.isNotEmpty(value)){
                for (int i = 0; i < keys.size(); i++){
                    //todo 3y
                    if (value.get(i) != null){
                        result.put(keys.get(i), value.get(i));
                    }
                }
            }
        } catch (Exception e) {
            log.error("redis mGet fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 借助pip 批量设置key-value以及过期时间
     * Lambda 形式
     * @param keyValues
     * @param seconds
     */
    public void pipelineSetEX(Map<String, String> keyValues, Long seconds) {
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                for (Map.Entry<String,String> entry: keyValues.entrySet()){
                    connection.setEx(entry.getKey().getBytes(), seconds, entry.getKey().getBytes());
                }
                return null;
            });
        } catch (Exception e) {
            log.error("redis pipelineSetEX fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
