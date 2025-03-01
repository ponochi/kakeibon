package org.panda.systems.kakeibon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration//( proxyBeanMethods = false )
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//    Logger logger = LoggerFactory.getLogger("Main");
//    logger.info("KakeiBon Ver.0.3.3");

        SpringApplication.run(Main.class, args);
    }
}
