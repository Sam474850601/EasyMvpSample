package cn.samblog.easymvp.sample.presenter.loading;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import cn.samblog.easymvp.sample.model.IUserModel;
import cn.samblog.easymvp.sample.model.UserModel;
import cn.samblog.easymvp.sample.ui.view.IWelcomeView;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.annotation.SingleInstance;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * 欢迎界面
 */
public class WelcomePresenter extends BasePresenter<IWelcomeView> implements IWelcomePresenter {

    @Inject
    Handler handler;


    @SingleInstance
    @Model(UserModel.class)
    IUserModel userModel;

    @Override
    public void initPeresenter(Bundle savedInstanceState, IWelcomeView view) {
        Log.e("MainPresenter", "initPeresenter");
        userModel.setUserName("Sam");
    }

    @Override
    public void onCreate(Context applicationContext) {
        super.onCreate(applicationContext);
        Log.e("MainPresenter", "onCreate");
    }


    @Override
    public void onStart() {
        Log.e("MainPresenter", "onStart");
    }


    @Override
    public void onPause() {
        Log.e("MainPresenter", "onPause");
    }


    @Override
    public void onStop() {
        Log.e("MainPresenter", "onStop");
    }

    @Override
    public void onResume() {
        Log.e("MainPresenter", "onResume");
    }

    @Override
    public void onRestart() {
        Log.e("MainPresenter", "onRestart");
    }

    @Override
    public void onDestroy() {
        Log.e("MainPresenter", "onDestroy");
        super.onDestroy();
    }


    @Override
    public void startReading() {
        IWelcomeView view = getView();
        if(null != view)
        {
            view.showLoadingView(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    IWelcomeView view = getView();
                    if(null != view)
                    {
                        view.showLoadingView(false);
                        view.forwordMainView();
                    }
                }
            }, 3*1000);
        }
    }


}
