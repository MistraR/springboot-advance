package com.advance.mistra;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@ComponentScan(basePackages = {"com.advance.mistra"})
@EntityScan("com.advance.mistra")
@EnableScheduling
@Configuration
public class JMTAutoConfiguration {

    @PostConstruct
    public void init(){

    }
}
