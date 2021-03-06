package cn.samblog.lib.easymvp.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import cn.samblog.lib.easymvp.presenter.IPresenter;
import cn.samblog.lib.easymvp.reciever.NotifyAllActivityBroadcastReciever;
import cn.samblog.lib.easymvp.utils.EasyHelper;
import sample.easymvp.lib.samblog.cn.easymvplib.R;

/**
 * Activity快速开发基类
 * @author Sam
 */
public abstract class BaseActivity extends AppCompatActivity implements  NotifyAllActivityBroadcastReciever.OnRecieveListener {
    private List<IPresenter> presenters;
    protected void setLayoutBefore() {
        addWindowTranslucentStatus();
    }

    protected void injectLayoutAfter(View view)
    {

    }


    protected void addWindowTranslucentStatus()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public void back(View view)
    {
        onBackPressed();
    }


    protected abstract void initViews(Bundle savedInstanceState,View parentView);



    protected  View mParentView;

    protected Bundle mSvedInstanceState;

    NotifyAllActivityBroadcastReciever notifyAllActivityBroadcastReciever;



    protected void onCreateBefore()
    {

    }


    @Deprecated
    @Override
    protected final  void onCreate(@Nullable Bundle savedInstanceState) {
        onCreateBefore();
        super.onCreate(savedInstanceState);
        mSvedInstanceState = savedInstanceState;
        setLayoutBefore();
        mParentView= EasyHelper.getLayoutView(this, this);
        setContentView(mParentView);
        notifyAllActivityBroadcastReciever = NotifyAllActivityBroadcastReciever.newInstance(getApplicationContext());
        notifyAllActivityBroadcastReciever.setOnRecieveListener(this);
    }

    //默认收到信息直接关闭退出
    @Override
    public void onRecieve(Intent intent) {
        String data =  intent.getStringExtra(NotifyAllActivityBroadcastReciever.PARAM_DATA);
        if (NotifyAllActivityBroadcastReciever.DATA_CLOSE_ACTIVITY.equals(data))
        {
            finish();
        }
    }




    @Override
    protected void onDestroy() {
        notifyAllActivityBroadcastReciever.release(getApplicationContext());
        EasyHelper.release(this);
        presenters = null;
        super.onDestroy();
    }





    @Override
    public void onContentChanged() {
        super.onContentChanged();
        presenters =   EasyHelper.inject(this, mParentView, getApplicationContext(), mSvedInstanceState, new EasyHelper.OnInitViewsCallback() {
            @Override
            public void onInit(View parentView) {
                injectLayoutAfter(mParentView);
                initViews(mSvedInstanceState,mParentView);
            }

        });

    }


    public void startActivity(Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        super.startActivity(intent);
    }

    public  IPresenter  getPresenter(Class<? extends  IPresenter> classType)
    {
        if(null != presenters)
        {
            for(IPresenter presenter : presenters)
            {
                if(presenter.getClass() == classType)
                {
                    return presenter;
                }
            }
        }
        return null;
    }


    public void startActivityFromLeftToRight(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left_to_right, R.anim.exit_right);
    }




    public void startActivityFromLeftToRight(Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivityFromLeftToRight(intent);
    }


    public void startActivityFromRightToLeft(Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivityFromRightToLeft(intent);
    }

    public void startActivityFromRightToLeft(Intent intent, int requestCode)
    {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.enter_from_right_to_left, R.anim.exit_left);
    }

    public void startActivityFromRightToLeft(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right_to_left, R.anim.exit_left);
    }


    public void startActivity(Class<? extends Activity> activityClass, int enterAnim, int exitAnim)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        super.startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }


    public void startActivitySlideLeft(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_left, 0);
    }

    public void startActivitySlideLeft(Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivitySlideLeft(intent);
    }


    public void startActivitySlideRight(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_right,0);
    }

    public void startActivitySlideRight(Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivitySlideRight(intent);
    }







}



