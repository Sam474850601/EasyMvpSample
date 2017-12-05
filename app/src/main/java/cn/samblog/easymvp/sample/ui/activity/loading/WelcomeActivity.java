package cn.samblog.easymvp.sample.ui.activity.loading;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.loading.IWelcomePresenter;
import cn.samblog.easymvp.sample.presenter.loading.WelcomePresenter;
import cn.samblog.easymvp.sample.ui.activity.user.UserActivity;
import cn.samblog.easymvp.sample.ui.view.loading.IWelcomeView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;
import cn.samblog.lib.easymvp.utils.EasyHelper;

/**
 * 欢迎界面
 */
@Resource(layoutResource = R.layout.acitivity_welcome)
public class WelcomeActivity extends BaseActivity  implements IWelcomeView {

    @Presenter(WelcomePresenter.class)
    IWelcomePresenter welcomePresenter;

    @Find(R.id.tv_title)
    TextView tvTitle;

    @Inject(hasContextParamConstructor = true)
    ProgressDialog progressDialog;

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        progressDialog.setMessage("正在加载中...");
        tvTitle.setText("you're welcome to use easymvp");
    }

    @OnClicked(R.id.btn_start)
    public void onStartCliked(View view)
    {
        welcomePresenter.startReading();
    }


    @Override
    public void showLoadingView(boolean isShow) {
        if(isShow)
        {
            progressDialog.show();
        }
        else
        {
            progressDialog.dismiss();
        }

    }

    @Override
    public void forwordUserView() {
        startActivityFromRightToLeft(UserActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        EasyHelper.clear();
        EasyHelper.release();
        super.onBackPressed();
    }
}
