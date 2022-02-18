package com.gg.msg.support.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 推送任务工具类
 * @author: GG
 * @date: 2022/2/15 7:33 下午
 */
public class TaskInfoUtils {

    private static int TYPE_FLAG = 1000000;

    /**
     * 生成BusinessId
     * 模板类型+模板ID+当天日期
     * (固定16位)
     */
    public static Long generateBusinessId(Long templateId, Integer templateType) {
        Integer today = Integer.valueOf(DateUtil.format(new Date(), "yyyyMMdd"));
        return Long.valueOf(String.format("%d%s", templateType * TYPE_FLAG + templateId, today));
    }
}
