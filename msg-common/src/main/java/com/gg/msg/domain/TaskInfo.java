package com.gg.msg.domain;

import com.gg.msg.dto.ContentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 发送任务信息
 * @author: GG
 * @date: 2022/2/14 11:56 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {
    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 业务Id
     */
    private Long businessId;

    /**
     * 接收者
     */
    private Set<String> receiver;

    /**
     * 发送的Id类型
     */
    private Integer idType;

    /**
     * 发送渠道
     */
    private Integer sendChannel;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 发送文案内容
     */
    private ContentModel contentModel;

    /**
     * 发送账号（邮件下可有多个发送账号、短信可有多个发送账号..）
     */
    private Integer sendAccount;

    /**
     * 消息去重时间 单位小时
     */
    private Integer deduplicationTime;

    /**
     * 是否夜间屏蔽
     * 0:不屏蔽
     * 1：屏蔽
     */
    private Integer isNightShield;
}
