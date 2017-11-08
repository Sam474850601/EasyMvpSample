package cn.samblog.easymvp.sample.presenter;

import android.os.Bundle;

import cn.samblog.easymvp.sample.ui.view.ILoginView;
import cn.samblog.easymvp.sample.ui.view.IUserView;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * 登录presenter
 */

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginPresenter {


    @Override
    public void initPeresenter(Bundle savedInstanceState, ILoginView view) {

    }
}
