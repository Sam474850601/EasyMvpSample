package cn.samblog.lib.easymvp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SharedPreferences字段
 * @author Sam
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheField {
    String DEFUALT = "NULL";
    String fieldName() default DEFUALT;

}


