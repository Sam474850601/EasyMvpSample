package cn.samblog.easymvp.sample.ui.activity.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.index.IMainPresenter;
import cn.samblog.easymvp.sample.presenter.index.MainPresenter;
import cn.samblog.easymvp.sample.ui.view.index.IMainView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;


@Resource(layoutResource = R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainView {

    @Presenter(MainPresenter.class)
    IMainPresenter mainPresenter;


    @Find(R.id.tv_username)
    TextView tvUsername;

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {

    }

    @Override
    public void initUsername(String username) {
        tvUsername.setText(username);
    }
}
