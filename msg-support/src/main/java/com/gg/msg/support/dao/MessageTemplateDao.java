package com.gg.msg.support.dao;

import com.gg.msg.support.domain.MessageTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: GG
 * @date: 2022/2/15 12:20 上午
 */
public interface MessageTemplateDao extends CrudRepository<MessageTemplate, Long> {
}
