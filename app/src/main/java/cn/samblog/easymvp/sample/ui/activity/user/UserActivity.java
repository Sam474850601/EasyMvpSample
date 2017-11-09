package cn.samblog.easymvp.sample.ui.activity.user;

import android.os.Bundle;
import android.view.View;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.ILoginPresenter;
import cn.samblog.easymvp.sample.presenter.IRegisterPresenter;
import cn.samblog.easymvp.sample.presenter.LoginPresenter;
import cn.samblog.easymvp.sample.presenter.RegisterPresenter;
import cn.samblog.easymvp.sample.ui.view.IUserView;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;

/**
 * 测试用户登录和用户注册mvp组件用例
 */
@Resource(layoutResource = R.layout.activity_user)
public class UserActivity extends BaseActivity implements IUserView{

    @Presenter( LoginPresenter.class)
    ILoginPresenter loginPresenter;

    @Presenter(RegisterPresenter.class)
    IRegisterPresenter registerPresenter;


    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {

    }
}
