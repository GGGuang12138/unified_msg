package com.gg.msg.handler.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: GG
 * @date: 2022/3/9 9:09 下午
 */
@Component
public class HandlerHolder {

    /**
     * 初始化为32，避免扩容
     */
    private Map<Integer, Handler> handlers = new HashMap<Integer, Handler>(32);

    public void putHandler(Integer channelCode, Handler handler) {
        handlers.put(channelCode, handler);
    }

    public Handler route(Integer channelCode) {
        return handlers.get(channelCode);
    }
}
