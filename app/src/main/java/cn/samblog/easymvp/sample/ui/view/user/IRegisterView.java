package cn.samblog.easymvp.sample.ui.view.user;

import cn.samblog.lib.easymvp.ui.view.IView;

/**
 *注册视图
 */

public interface IRegisterView  extends IView {
    String getRegisterUsername();
    void forwardMainView();
}
