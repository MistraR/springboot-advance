package com.advance.mistra.config;

import com.advance.mistra.JMTAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import({JMTAutoConfiguration.class})
public @interface EnableJMT {
}
