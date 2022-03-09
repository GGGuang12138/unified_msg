package com.gg.msg.handler.handler;

import com.gg.msg.domain.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author: GG
 * @date: 2022/3/9 9:06 下午
 */
public abstract class BaseHandler implements Handler{

    /**
     * 标识渠道的Code
     * 子类初始化的时候指定
     */
    protected Integer channelCode;

    @Autowired
    private HandlerHolder handlerHolder;

    /**
     * 初始化渠道与Handler的映射关系
     */
    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }

    public void doHandler(TaskInfo taskInfo) {
        handler(taskInfo);
    }

    /**
     * 统一处理的handler接口
     *
     * @param taskInfo
     * @return
     */
    public abstract void handler(TaskInfo taskInfo);
}
