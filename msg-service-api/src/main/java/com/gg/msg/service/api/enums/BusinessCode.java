package com.gg.msg.service.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author: GG
 * @date: 2022/2/15 4:46 下午
 */
@Getter
@ToString
@AllArgsConstructor
public enum BusinessCode {
    /** 普通发送流程 */
    COMMON_SEND("send", "普通发送"),

    /** 撤回流程 */
    RECALL("recall", "撤回消息");

    /** code 关联着责任链的模板 */
    private String code;

    /** 类型说明 */
    private String description;
}