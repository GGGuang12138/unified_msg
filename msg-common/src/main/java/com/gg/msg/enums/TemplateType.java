package com.gg.msg.enums;

/**
 * 模版类型
 * @author: GG
 * @date: 2022/2/14 11:53 下午
 */
public enum TemplateType {
    OPERATION(10, "运营类的模板"),
    TECHNOLOGY(20, "技术类的模板"),
            ;

    private Integer code;
    private String description;

    TemplateType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
