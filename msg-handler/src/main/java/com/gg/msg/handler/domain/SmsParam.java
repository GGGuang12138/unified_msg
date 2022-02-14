package com.gg.msg.handler.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author: GG
 * @date: 2022/2/13 9:31 下午
 */
@Data
@Builder
public class SmsParam {

    /**
     * 业务Id
     */
    private Long messageTemplateId;

    /**
     * 需要发送的手机号
     */
    private Set<String> phones;

    /**
     * 发送文案
     */
    private String content;

    /**
     * 发送账号
     */
    private Integer sendAccount;
}
