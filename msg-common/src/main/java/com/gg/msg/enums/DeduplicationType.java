package com.gg.msg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 去重类型枚举
 * @author: GG
 * @date: 2022/4/1 5:45 下午
 */
@Getter
@ToString
@AllArgsConstructor
public enum DeduplicationType {

    CONTENT(10,"N分钟内相同内容去重"),
    FREQUENCY(20,"一天内N次相同渠道去重"),
    ;

    private Integer code;
    private String description;


    /**
     * 获取全部去重类型的列表
     * @return
     */
    public static List<Integer> getDeduplicationList() {
        ArrayList<Integer> result = new ArrayList<>();
        for (DeduplicationType value : DeduplicationType.values()) {
            result.add(value.getCode());
        }
        return result;
    }
}
