package com.gg.msg.service.api.impl.config;

import com.gg.msg.service.api.enums.BusinessCode;
import com.gg.msg.service.api.impl.action.AssembleAction;
import com.gg.msg.service.api.impl.action.PreParamCheckAction;
import com.gg.msg.service.api.impl.action.SendMqAction;
import com.gg.msg.support.pipeline.BusinessProcess;
import com.gg.msg.support.pipeline.ProcessController;
import com.gg.msg.support.pipeline.ProcessTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: GG
 * @date: 2022/2/15 5:47 下午
 */
@Configuration
public class PipelineConfig {

    /**
     * pipeline流程控制器
     * 目前暂定只有 普通发送的流程
     * 后续扩展则加BusinessCode和ProcessTemplate
     * @return
     */
    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>();
        // TODO 加入其他流程模版
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        ArrayList<BusinessProcess> processList = new ArrayList<>();

        processList.add(preParamCheckAction());
        processList.add(assembleAction());
        processList.add(sendMqAction());

        processTemplate.setProcessList(processList);
        return processTemplate;
    }

    /**
     * 组装参数Action
     * @return
     */
    @Bean
    public AssembleAction assembleAction() {
        return new AssembleAction();
    }

    /**
     * 参数校验Action
     * @return
     */
    @Bean
    public PreParamCheckAction preParamCheckAction() {
        return new PreParamCheckAction();
    }

    /**
     * 发送消息至MQ的Action
     * @return
     */
    @Bean
    public SendMqAction sendMqAction() {
        return new SendMqAction();
    }
}
