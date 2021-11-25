package com.advance.mistra.plugin.esannotationversion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.advance.mistra.plugin.esannotationversion.enums.EsQueryTypeEnum;

/**
 * 定义查询字段的查询方式
 *
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Repeatable(EsQueryFieldMultiple.class)
public @interface EsQueryField {

    /**
     * 查询参数
     *
     * @return 查询字段
     */
    String key() default "";

    /**
     * 查询类型 see{@link EsQueryTypeEnum}
     *
     * @return EsQueryTypeEnum
     */
    EsQueryTypeEnum type() default EsQueryTypeEnum.EQUAL;

    /**
     * 范围查询 from后缀
     *
     * @return from后缀
     */
    String fromSuffix() default "From";

    /**
     * 范围查询 to后缀
     *
     * @return to后缀
     */
    String toSuffix() default "To";

    /**
     * 多个字段分隔符
     *
     * @return 分隔符
     */
    String separator() default ",";

    /**
     * 指定对象的哪个字段将应用于查询映射
     * 例如：
     * 同一个文档下有多个User对象，对象名分别为createdUser、updatedUser，该User对象的属性有name等字段，
     * 如果要根据查询createdUser的name来进行查询，
     * 则可以这样定义DefinitionQuery：queryField = cName, mapped = createdUser.name
     *
     * @return 映射的实体的字段路径
     */
    String mapped() default "";

    /**
     * 嵌套查询的path
     *
     * @return path
     */
    String nestedPath() default "";
}
