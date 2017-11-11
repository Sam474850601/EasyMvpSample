package cn.samblog.easymvp.sample.presenter.loading;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import cn.samblog.easymvp.sample.model.loading.ILoadingModel;
import cn.samblog.easymvp.sample.model.loading.LoadingModel;
import cn.samblog.easymvp.sample.model.loading.callback.ILoadingCallback;
import cn.samblog.easymvp.sample.ui.view.loading.IWelcomeView;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * 欢迎界面
 */
public class WelcomePresenter extends BasePresenter<IWelcomeView> implements IWelcomePresenter {


    @Model(LoadingModel.class)
    ILoadingModel loadingModel;

    @Override
    public void onCreate(Context applicationContext) {
        super.onCreate(applicationContext);
        Log.e("WelcomePresenter", "onCreate");
    }

    @Override
    public void initPeresenter(Bundle savedInstanceState, IWelcomeView view) {
        Log.e("WelcomePresenter", "initPeresenter");

    }

    @Override
    public void startReading() {
        IWelcomeView view = getView();
        if(null != view)
        {
            view.showLoadingView(true);
            loadingModel.loading(new ILoadingCallback() {
                @Override
                public void onCompleted() {
                    IWelcomeView view = getView();
                    if(null != view)
                    {
                        view.showLoadingView(false);
                        view.forwordUserView();
                    }
                }
            });
        }
    }


    @Override
    public void onStart() {
        Log.e("WelcomePresenter", "onStart");
    }


    @Override
    public void onPause() {
        Log.e("WelcomePresenter", "onPause");
    }


    @Override
    public void onStop() {
        Log.e("WelcomePresenter", "onStop");
    }

    @Override
    public void onResume() {
        Log.e("WelcomePresenter", "onResume");
    }



    @Override
    public void onDestroy() {
        Log.e("WelcomePresenter", "onDestroy");
        super.onDestroy();
    }



}
