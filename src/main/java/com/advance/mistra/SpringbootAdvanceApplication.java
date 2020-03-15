package com.advance.mistra;

import com.advance.mistra.common.BaseApplication;
import com.advance.mistra.plugin.es.autowired.EnableEsClientModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 13:04
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableEsClientModule
public class SpringbootAdvanceApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvanceApplication.class, args);
    }

}
