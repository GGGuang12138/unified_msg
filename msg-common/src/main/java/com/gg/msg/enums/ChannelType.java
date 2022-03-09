package com.gg.msg.enums;

import com.gg.msg.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 渠道类型
 * @author: GG
 * @date: 2022/2/15 8:16 下午
 */
@Getter
@ToString
@AllArgsConstructor
public enum ChannelType {
    IM(10, "IM(站内信)", ImContentModel.class,"im"),
    PUSH(20, "push(通知栏)", PushContentModel.class,"push"),
    SMS(30, "sms(短信)", SmsContentModel.class,"sms"),
    EMAIL(40, "email(邮件)", EmailContentModel.class,"email"),
    OFFICIAL_ACCOUNT(50, "OfficialAccounts(服务号)", OfficialAccountsContentModel.class,"official_account"),
    MINI_PROGRAM(60, "miniProgram(小程序)", MiniProgramContentModel.class,"mini_program"),
    ;

    private Integer code;
    private String description;
    private Class contentModelClass;
    private String codeEn;


    public static Class getChanelModelClassByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value.getContentModelClass();
            }
        }
        return null;
    }

    /**
     * 通过code获取enum
     * @param code
     * @return
     */
    public static ChannelType getEnumByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}

