package cn.samblog.easymvp.sample.presenter.index;

import android.os.Bundle;
import android.util.Log;

import cn.samblog.easymvp.sample.model.user.IUserModel;
import cn.samblog.easymvp.sample.model.user.UserModel;
import cn.samblog.easymvp.sample.ui.view.index.IMainView;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.annotation.SingleInstance;
import cn.samblog.lib.easymvp.presenter.BasePresenter;
import cn.samblog.lib.easymvp.ui.view.IView;
import cn.samblog.lib.easymvp.utils.EasyHelper;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MainPresenter extends BasePresenter<IMainView> implements IMainPresenter {


    @SingleInstance
    @Model(UserModel.class)
    IUserModel userModel;


    @Override
    public void initPeresenter(Bundle savedInstanceState, IMainView view) {
        view.initUsername(userModel.getUserName());
    }

    @Override
    public void onDestroy() {
        EasyHelper.clear();
        EasyHelper.release();
        super.onDestroy();
    }
}
