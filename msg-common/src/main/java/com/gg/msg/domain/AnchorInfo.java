package com.gg.msg.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 日志埋点信息
 * @author: GG
 * @date: 2022/4/1 6:44 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnchorInfo {

    /**
     * 发送用户
     */
    private Set<String> ids;

    /**
     * 具体点位
     */
    private int state;

    /**
     * 业务Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private Long businessId;


    /**
     * 生成时间
     */
    private long timestamp;
}
