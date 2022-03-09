package com.gg.msg.handler.config;

import com.gg.msg.handler.pending.TaskThread;
import com.gg.msg.handler.receiver.Receiver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 多例 Bean 配置
 * @author: GG
 * @date: 2022/3/9 8:13 下午
 */
@Configuration
public class PrototypeBeanConfig {
    /**
     * 定义多例的Receiver
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Receiver receiver() {
        return new Receiver();
    }

    /**
     * 定义多例的TaskThread
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public TaskThread task() {
        return new TaskThread();
    }
}
