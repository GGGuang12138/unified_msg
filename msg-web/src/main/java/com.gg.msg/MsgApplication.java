package com.gg.msg;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages={"com.gg.msg.*.dao"})
public class MsgApplication {

    public static void main(String[] args) {
        System.setProperty("apollo.config-service", "http://ip:7000");
        SpringApplication.run(MsgApplication.class, args);
    }


}