package com.gg.msg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 返回结果状态
 * @author: GG
 * @date: 2022/2/15 1:51 下午
 */
@Getter
@ToString
@AllArgsConstructor
public enum ResultStatus {
    /**
     * OK：操作成功
     */
    SUCCESS("00000", "操作成功"),
    FAIL("00001", "操作失败"),

    /**
     * 客户端
     */
    CLIENT_BAD_PARAMETERS("C0100", "客户端参数错误"),

    /**
     * 系统
     */
    SERVICE_ERROR("S0001", "服务执行异常"),
    RESOURCE_NOT_FOUND("S0404", "资源不存在"),

    /**
     * pipeline
     */
    CONTEXT_IS_NULL("P0001","流程上下文为空"),
    BUSINESS_CODE_IS_NULL("P0002","业务代码为空"),
    PROCESS_TEMPLATE_IS_NULL("P0003","流程模板配置为空"),
    PROCESS_LIST_IS_NULL("P0004","业务处理器配置为空" ),
    ;

    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;
}
