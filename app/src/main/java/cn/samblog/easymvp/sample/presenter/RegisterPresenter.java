package cn.samblog.easymvp.sample.presenter;

import android.os.Bundle;

import cn.samblog.easymvp.sample.ui.view.ILoginView;
import cn.samblog.easymvp.sample.ui.view.IRegisterView;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * 注册Presenter
 */

public class RegisterPresenter extends BasePresenter<IRegisterView> implements IRegisterPresenter {

    @Override
    public void initPeresenter(Bundle savedInstanceState, IRegisterView view) {

    }
}
