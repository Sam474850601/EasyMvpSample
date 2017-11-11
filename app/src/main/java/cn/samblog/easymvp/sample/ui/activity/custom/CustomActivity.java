package cn.samblog.easymvp.sample.ui.activity.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.custom.CustomPresenter;
import cn.samblog.easymvp.sample.presenter.custom.ICustomPresenter;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.presenter.IPresenter;
import cn.samblog.lib.easymvp.ui.view.IView;
import cn.samblog.lib.easymvp.utils.EasyHelper;

/**
 * 如果不想使用BaseActivity, 可以这样自定义.但是初始化代码必须要
 */
@Resource(layoutResource = R.layout.activity_custom)
public class CustomActivity extends Activity implements IView  {

    View rootView;

    Bundle savedInstanceState;


    @Presenter(CustomPresenter.class)
    ICustomPresenter customPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        rootView =  EasyHelper.getLayoutView(this, this);
        setContentView(rootView);
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        EasyHelper.inject(this, rootView, getApplicationContext(), savedInstanceState, new EasyHelper.OnInitViewsCallback() {

            @Override
            public void onInit(View parentView) {
                initViews(parentView);
            }
        });
    }

    private void initViews(View parentView)
    {
        //初始化View
    }
}
