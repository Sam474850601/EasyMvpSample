package cn.samblog.easymvp.sample.ui.activity.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.user.ILoginPresenter;
import cn.samblog.easymvp.sample.presenter.user.IRegisterPresenter;
import cn.samblog.easymvp.sample.presenter.user.LoginPresenter;
import cn.samblog.easymvp.sample.presenter.user.RegisterPresenter;
import cn.samblog.easymvp.sample.ui.activity.index.MainActivity;
import cn.samblog.easymvp.sample.ui.view.user.IUserView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;
import cn.samblog.lib.easymvp.utils.EasyHelper;

/**
 * 测试用户登录和用户注册mvp组件用例 ,
 * 当一个界面出现2个模块时候， 可以注入多个presenter分担编码压力
 */
@Resource(layoutResource = R.layout.activity_user)
public class UserActivity extends BaseActivity implements IUserView{


    @Presenter( LoginPresenter.class)
    ILoginPresenter loginPresenter;


    @Presenter(RegisterPresenter.class)
    IRegisterPresenter registerPresenter;

    @Find(R.id.et_loginusername)
    EditText etLoginUserName;


    @Find(R.id.et_registerusername)
    EditText etRegisterUserName;

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        Log.e("initViews", "");
    }


    @OnClicked(R.id.btn_login)
    void onLoginCliked(View view)
    {
        loginPresenter.login();
    }

    @OnClicked(R.id.btn_register)
    void onRegisterCliked(View view)
    {
        registerPresenter.register();
    }


    @Override
    public String getLoginUsername() {
        String name = etLoginUserName.getText().toString();
        return name;
    }

    @Override
    public void forwardMainView() {
        startActivityFromRightToLeft(MainActivity.class);
        finish();
    }

    @Override
    public String getRegisterUsername() {
        return  etRegisterUserName.getText().toString();
    }


    @Override
    public void onBackPressed() {
        EasyHelper.clear();
        EasyHelper.release();
        super.onBackPressed();
    }

}
