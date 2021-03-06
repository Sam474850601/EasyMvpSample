package cn.samblog.easymvp.sample.ui.activity.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.index.IMainPresenter;
import cn.samblog.easymvp.sample.presenter.index.MainPresenter;
import cn.samblog.easymvp.sample.ui.activity.custom.CustomActivity;
import cn.samblog.easymvp.sample.ui.activity.other.RecyclerViewAdapterDemo1Activity;
import cn.samblog.easymvp.sample.ui.activity.other.RecyclerViewAdapterDemo2Activity;
import cn.samblog.easymvp.sample.ui.activity.other.SimpleFragmentActivity;
import cn.samblog.easymvp.sample.ui.view.index.IMainView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;


@Resource(layoutResource = R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainView {

    @Presenter(MainPresenter.class)
    IMainPresenter mainPresenter;

    @Find(R.id.tv_username)
    TextView tvUsername;

    @OnClicked(R.id.btn_custom)
    void forwardCustom(View view)
    {
        startActivityFromLeftToRight(CustomActivity.class);
    }

    @OnClicked(R.id.btn_recyclerviewadapter)
    void forwardRecyclerViewAdapterDemo1(View view)
    {
        startActivityFromLeftToRight(RecyclerViewAdapterDemo1Activity.class);
    }


    @OnClicked(R.id.btn_recyclerviewadapter2)
    void forwardRecyclerViewAdapterDemo2(View view)
    {
        startActivityFromLeftToRight(RecyclerViewAdapterDemo2Activity.class);
    }


    @OnClicked(R.id.btn_fragment)
    void forwardFragment(View view)
    {
        startActivityFromRightToLeft(SimpleFragmentActivity.class);
    }

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {

    }

    @Override
    public void initUsername(String username) {
        tvUsername.setText(username);
    }


}
