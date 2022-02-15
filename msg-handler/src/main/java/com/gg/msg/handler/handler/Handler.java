package com.gg.msg.handler.handler;

import com.gg.msg.domain.TaskInfo;

/**
 * @author: GG
 * @date: 2022/2/15 12:00 上午
 */
public interface Handler {

    boolean doHandler(TaskInfo TaskInfo);
}
