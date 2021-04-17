package com.wty.maill.annotation;

import com.wty.maill.enumeration.DataSourceType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceCondition {

    DataSourceType value() default DataSourceType.MASTER;

}
