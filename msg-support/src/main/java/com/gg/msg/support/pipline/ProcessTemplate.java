package com.gg.msg.support.pipline;

import java.util.List;

/**
 * 业务执行模版（作用是将多个业务逻辑串起来）
 * @author: GG
 * @date: 2022/2/15 4:31 下午
 */
public class ProcessTemplate {

    private List<BusinessProcess> processList;

    public List<BusinessProcess> getProcessList() {
        return processList;
    }
    public void setProcessList(List<BusinessProcess> processList) {
        this.processList = processList;
    }
}
