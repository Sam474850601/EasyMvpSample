package cn.samblog.easymvp.sample.ui.view.loading;

import cn.samblog.lib.easymvp.ui.view.IView;

/**
 * Created by Administrator on 2017/11/9.
 */

public interface IWelcomeView extends IView {
    void showLoadingView(boolean isShow);
    void forwordUserView();
}
