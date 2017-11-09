package cn.samblog.easymvp.sample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.model.IUserModel;
import cn.samblog.easymvp.sample.model.UserModel;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Model;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.annotation.SingleInstance;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;


@Resource(layoutResource = R.layout.activity_main)
public class MainActivity extends BaseActivity  {

    @Find(R.id.tv_username)
    TextView tvUsername;

    @SingleInstance
    @Model(UserModel.class)
    IUserModel userModel;




    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        tvUsername.setText(userModel.getUserName());


    }
}
