package com.gg.msg.support.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gg.msg.enums.ResultStatus;
import com.gg.msg.vo.BasicResultVO;

import java.util.List;
import java.util.Map;

/**
 * 处理器
 * @author: GG
 * @date: 2022/2/15 4:35 下午
 */
public class ProcessController {
    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate> templateConfig = null;

    public Map<String, ProcessTemplate> getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(Map<String, ProcessTemplate> templateConfig) {
        this.templateConfig = templateConfig;
    }

    /**
     * 执行责任链
     *
     * @param context
     * @return 返回上下文内容
     */
    public ProcessContext process(ProcessContext context) {

        /**
         * 前置检查
         */
        if (!preCheck(context)) {
            return context;
        }

        /**
         * 遍历流程节点
         */
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess : processList) {
            businessProcess.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }
        return context;
    }

    private Boolean preCheck(ProcessContext context) {
        // 上下文
        if (context == null) {
            context = new ProcessContext();
            context.setResponse(BasicResultVO.fail(ResultStatus.CONTEXT_IS_NULL));
            return false;
        }

        // 业务代码
        String businessCode = context.getCode();
        if (StrUtil.isBlank(businessCode)) {
            context.setResponse(BasicResultVO.fail(ResultStatus.BUSINESS_CODE_IS_NULL));
            return false;
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (processTemplate == null) {
            context.setResponse(BasicResultVO.fail(ResultStatus.PROCESS_TEMPLATE_IS_NULL));
            return false;
        }

        // 执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            context.setResponse(BasicResultVO.fail(ResultStatus.PROCESS_LIST_IS_NULL));
            return false;
        }

        return true;
    }

}
