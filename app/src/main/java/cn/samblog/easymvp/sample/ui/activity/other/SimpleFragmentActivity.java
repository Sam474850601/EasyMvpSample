package cn.samblog.easymvp.sample.ui.activity.other;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.other.FirstFragmentPresenter;
import cn.samblog.easymvp.sample.presenter.other.IFirstFragmentPresenter;
import cn.samblog.easymvp.sample.presenter.other.ISecondFragmentPresenter;
import cn.samblog.easymvp.sample.presenter.other.SecondFragmentPresenter;
import cn.samblog.easymvp.sample.ui.fragment.FirstFragment;
import cn.samblog.easymvp.sample.ui.fragment.SecondFragment;
import cn.samblog.easymvp.sample.ui.view.other.ISimpleFragmentView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;
import cn.samblog.lib.easymvp.ui.view.IView;

/**
 *Fragment 简单使用
 */
@Resource(layoutResource = R.layout.activity_simplefragment)
public class SimpleFragmentActivity extends BaseActivity implements ISimpleFragmentView {


    @Presenter(FirstFragmentPresenter.class)
    public IFirstFragmentPresenter firstFragmentPresenter;

    @Presenter(SecondFragmentPresenter.class)
    public ISecondFragmentPresenter secondFragmentPresenter;

    @Inject
    FirstFragment firstFragment;



    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add( R.id.container,firstFragment);
        transaction.commitNow();
    }


    @Override
    public void setFirstViewMessage(String message) {
        firstFragment.setFirstViewMessage(message);
    }

    @Override
    public void setSecondMessage(String message) {
        firstFragment.setSecondMessage(message);
    }
}
