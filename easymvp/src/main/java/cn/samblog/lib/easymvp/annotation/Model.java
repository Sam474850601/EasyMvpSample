package cn.samblog.lib.easymvp.annotation;

import cn.samblog.lib.easymvp.model.ContextModel;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sam
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
    Class<? extends ContextModel> classType();
}
