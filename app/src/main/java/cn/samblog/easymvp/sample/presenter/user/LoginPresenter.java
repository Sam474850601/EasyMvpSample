package cn.samblog.easymvp.sample.presenter.user;

import android.os.Bundle;

import cn.samblog.easymvp.sample.model.user.IUserModel;
import cn.samblog.easymvp.sample.model.user.UserModel;
import cn.samblog.easymvp.sample.ui.view.user.ILoginView;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.annotation.SingleInstance;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * 登录presenter
 */

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginPresenter {


    @SingleInstance
    @Model(UserModel.class)
    IUserModel userModel;

    @Override
    public void initPeresenter(Bundle savedInstanceState, ILoginView view) {

    }

    @Override
    public void login() {
        ILoginView view = getView();
        if(null != view)
        {
            userModel.setUserName(view.getLoginUsername());
            view.forwardMainView();
        }
    }
}
