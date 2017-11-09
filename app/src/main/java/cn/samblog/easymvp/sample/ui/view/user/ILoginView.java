package cn.samblog.easymvp.sample.ui.view.user;

import cn.samblog.lib.easymvp.ui.view.IView;

/**
 * 登录视图
 */

public interface ILoginView extends IView {
    String getLoginUsername();
    void forwardMainView();

}
