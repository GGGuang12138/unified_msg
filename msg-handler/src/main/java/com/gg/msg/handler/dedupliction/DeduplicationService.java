package com.gg.msg.handler.dedupliction;

import com.gg.msg.handler.domain.DeduplicationParam;

/**
 * 去重接口
 * @author: GG
 * @date: 2022/3/11 11:12 下午
 */
public interface DeduplicationService {

    /**
     * 去重
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
