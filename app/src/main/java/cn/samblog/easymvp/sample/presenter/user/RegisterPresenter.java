package cn.samblog.easymvp.sample.presenter.user;

import android.os.Bundle;

import cn.samblog.easymvp.sample.model.user.IUserModel;
import cn.samblog.easymvp.sample.model.user.UserModel;
import cn.samblog.easymvp.sample.ui.view.user.IRegisterView;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.annotation.SingleInstance;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * 注册Presenter
 */

public class RegisterPresenter extends BasePresenter<IRegisterView> implements IRegisterPresenter {



    @Override
    public void initPeresenter(Bundle savedInstanceState, IRegisterView view) {

    }

    @SingleInstance
    @Model(UserModel.class)
    IUserModel userModel;

    @Override
    public void register() {
        IRegisterView view = getView();
        if(null != view)
        {
            userModel.setUserName(view.getRegisterUsername());
            view.forwardMainView();
        }

    }
}
