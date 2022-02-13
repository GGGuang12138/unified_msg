package com.gg.msg;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MsgApplication {

    private final Logger logger = LoggerFactory.getLogger(MsgApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("info msg ");
        logger.error("error msg ");
        return String.format("Hello %s!", name);
    }

}