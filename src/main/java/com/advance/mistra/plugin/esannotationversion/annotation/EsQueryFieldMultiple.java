package com.advance.mistra.plugin.esannotationversion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface EsQueryFieldMultiple {

    EsQueryField[] value();
}
