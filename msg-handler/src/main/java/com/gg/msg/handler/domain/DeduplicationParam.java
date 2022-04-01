package com.gg.msg.handler.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.gg.msg.domain.TaskInfo;
import com.gg.msg.enums.AnchorState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用去重参数
 * @author: GG
 * @date: 2022/3/11 11:13 下午
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {

    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 标识属于哪种去重(数据埋点)
     */
    private AnchorState anchorState;
}
