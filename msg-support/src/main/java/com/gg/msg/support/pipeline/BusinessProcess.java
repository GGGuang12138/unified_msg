package com.gg.msg.support.pipeline;

/**
 * 业务逻辑
 * @author: GG
 * @date: 2022/2/15 4:30 下午
 */
public interface BusinessProcess {
    /**
     * 真正处理逻辑
     * @param context
     */
    void process(ProcessContext context);
}
