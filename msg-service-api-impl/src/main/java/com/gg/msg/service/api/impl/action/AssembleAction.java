package com.gg.msg.service.api.impl.action;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gg.msg.constant.BaseConstant;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.dto.ContentModel;
import com.gg.msg.enums.ChannelType;
import com.gg.msg.enums.ResultStatus;
import com.gg.msg.service.api.domain.MessageParam;
import com.gg.msg.service.api.impl.domain.SendTaskModel;
import com.gg.msg.support.dao.MessageTemplateDao;
import com.gg.msg.support.domain.MessageTemplate;
import com.gg.msg.support.pipeline.BusinessProcess;
import com.gg.msg.support.pipeline.ProcessContext;
import com.gg.msg.support.utils.ContentHolderUtil;
import com.gg.msg.support.utils.TaskInfoUtils;
import com.gg.msg.vo.BasicResultVO;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author: GG
 * @date: 2022/2/15 5:02 下午
 */
@Slf4j
public class AssembleAction implements BusinessProcess {

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        try {
            Optional<MessageTemplate> messageTemplate = messageTemplateDao.findById(messageTemplateId);
            if (!messageTemplate.isPresent() || messageTemplate.get().getIsDeleted().equals(BaseConstant.TRUE)){
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(ResultStatus.CLIENT_BAD_PARAMETERS));
                return;
            }
            sendTaskModel.setTaskInfo(assembleTaskInfo(sendTaskModel,messageTemplate.get()));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(ResultStatus.SERVICE_ERROR));
            log.error("assemble task fail! templateId:{}, e:{}", messageTemplateId, Throwables.getStackTraceAsString(e));

        }
    }

    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate){
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();

        for (MessageParam messageParam : messageParamList) {
            TaskInfo taskInfo = TaskInfo.builder()
                    .messageTemplateId(messageTemplate.getId())
                    // businessId由工具类生成（模版ID、模版类型）
                    .businessId(TaskInfoUtils.generateBusinessId(messageTemplate.getId(), messageTemplate.getTemplateType()))
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(String.valueOf(StrUtil.C_COMMA)))))
                    .idType(messageTemplate.getIdType())
                    .sendChannel(messageTemplate.getSendChannel())
                    .templateType(messageTemplate.getTemplateType())
                    .msgType(messageTemplate.getMsgType())
                    .sendAccount(messageTemplate.getSendAccount())
                    // 发送任务的内容 （由模版、消息参数生成）
                    .contentModel(getContentModelValue(messageTemplate, messageParam))
                    .deduplicationTime(messageTemplate.getDeduplicationTime())
                    .isNightShield(messageTemplate.getIsNightShield()).build();

            taskInfoList.add(taskInfo);
        }

        return taskInfoList;
    }

    private static ContentModel getContentModelValue(MessageTemplate messageTemplate, MessageParam messageParam) {
        // 获取入参
        Map<String, String> variables = messageParam.getVariables();

        // 通过反射获取 contentModel
        Class contentModelClass = ChannelType.getChanelModelClassByCode(messageTemplate.getSendChannel());
        ContentModel contentModel = (ContentModel) ReflectUtil.newInstance(contentModelClass);

        // 获取模版
        JSONObject jsonObject = JSON.parseObject(messageTemplate.getMsgContent());
        // 匹配模版和入参，填充到contentModel的属性
        Field[] fields = ReflectUtil.getFields(contentModelClass);
        for (Field field : fields) {
            // 将模版中的占位符换成要传入的参数
            String originValue = jsonObject.getString(field.getName());
            if (StrUtil.isNotBlank(originValue)) {
                String resultValue = ContentHolderUtil.replacePlaceHolder(originValue, variables);
                ReflectUtil.setFieldValue(contentModel, field, resultValue);
            }
        }

        return contentModel;
    }
}
