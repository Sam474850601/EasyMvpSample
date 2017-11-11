package cn.samblog.easymvp.sample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.other.IFirstFragmentPresenter;
import cn.samblog.easymvp.sample.ui.activity.other.SimpleFragmentActivity;
import cn.samblog.easymvp.sample.ui.view.other.IFirstView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.fragment.BaseFragment;

/**
 * Activity的Fragment
 */
@Resource(layoutResource = R.layout.fragment_firstfragment)
public class FirstFragment extends BaseFragment<SimpleFragmentActivity> implements IFirstView {

    @Find(R.id.tv_message)
    TextView tvMessage;

    @Inject
    SecondFragment secondFragment;


    @OnClicked(R.id.btn_answer)
    void onAnswerCliked(View view)
    {
       IFirstFragmentPresenter presenter =  getBaseActivity().firstFragmentPresenter;
       presenter.loadMessage();
    }

    @Override
    protected void onPrepare(View view) {
        //如果使用了AutoLayout, 可以在这加AutoUtils.auto(view);
    }

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add( R.id.childcontainer,secondFragment);
        transaction.commitNow();

    }

    @Override
    public void setFirstViewMessage(String message) {
        tvMessage.setText(message);
    }

    @Override
    public void setSecondMessage(String message) {
        secondFragment.setSecondMessage(message);
    }
}
