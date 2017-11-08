package cn.samblog.lib.easymvp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.samblog.lib.easymvp.model.ContextModel;
import cn.samblog.lib.easymvp.presenter.BasePresenter;
import cn.samblog.lib.easymvp.presenter.IPresenter;
import cn.samblog.lib.easymvp.ui.view.IView;

/**
 * presenter 注解
 * @author Sam
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Presenter {
    Class<? extends IPresenter> classType();
}
