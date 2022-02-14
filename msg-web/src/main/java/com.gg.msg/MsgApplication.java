package com.gg.msg;


import com.gg.msg.handler.domain.SmsParam;
import com.gg.msg.handler.script.TencentSmsScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@RestController
public class MsgApplication {

    private final Logger logger = LoggerFactory.getLogger(MsgApplication.class);

    @Autowired
    private TencentSmsScript tencentSmsScript;

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("info msg ");
        logger.error("error msg ");

        SmsParam smsParam = SmsParam.builder()
                .phones(new HashSet<>(Arrays.asList("//")))
                .content("3333")
                .build();

        try {
            tencentSmsScript.send(smsParam);
        } catch (Exception exception) {
            logger.error("发送失败");
        }

        return String.format("Hello %s!", name);
    }

}