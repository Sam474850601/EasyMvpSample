package cn.samblog.lib.easymvp.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.presenter.BasePresenter;
import cn.samblog.lib.easymvp.presenter.IPresenter;
import cn.samblog.lib.easymvp.reciever.NotifyAllActivityBroadcastReciever;
import cn.samblog.lib.easymvp.ui.view.IView;
import cn.samblog.lib.easymvp.utils.ClassUtil;
import cn.samblog.lib.easymvp.utils.EasyHelper;
import rx.Observable;
import rx.functions.Action1;
import rx.observers.Observers;
import sample.easymvp.lib.samblog.cn.easymvplib.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Activity快速开发基类
 * @author Sam
 */
public abstract class BaseActivity<P extends BasePresenter>  extends AppCompatActivity implements  NotifyAllActivityBroadcastReciever.OnRecieveListener {
    private SparseArray<View> views =new SparseArray<>();
    private ArrayList<IPresenter> presenters = new ArrayList<>();
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
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
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
    protected void onStop() {
        _stopPresenters();

        super.onStop();
    }

    private void _stopPresenters() {
        Observable.from(presenters).subscribe(new Action1<IPresenter>() {
            @Override
            public void call(IPresenter presenter) {
                presenter.onStop();
            }
        });
    }


    private void _pausePresenters() {
        Observable.from(presenters).subscribe(new Action1<IPresenter>() {
            @Override
            public void call(IPresenter presenter) {
                presenter.onPause();
            }
        });
    }
    private void _resumePresenters() {
        Observable.from(presenters).subscribe(new Action1<IPresenter>() {
            @Override
            public void call(IPresenter presenter) {
                presenter.onResume();
            }
        });
    }

    private void _restartPresenters() {
        Observable.from(presenters).subscribe(new Action1<IPresenter>() {
            @Override
            public void call(IPresenter presenter) {
                presenter.onRestart();
            }
        });
    }


    private void _startPresenters() {
        Observable.from(presenters).subscribe(new Action1<IPresenter>() {
            @Override
            public void call(IPresenter presenter) {
                presenter.onStart();
            }
        });
    }


    @Override
    protected void onPause() {
        _pausePresenters();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _resumePresenters();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        _restartPresenters();
    }

    @Override
    protected void onStart() {
        super.onStart();
        _startPresenters();
    }

    @Override
    protected void onDestroy() {
        notifyAllActivityBroadcastReciever.release(getApplicationContext());
        _destroyPresenters();
        super.onDestroy();
    }


    private void _destroyPresenters() {
        Observable.from(presenters).subscribe(new Action1<IPresenter>() {
            @Override
            public void call(IPresenter presenter) {
                presenter.onDestroy();
            }
        });
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transition = fragmentManager.beginTransaction();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(null != fragmentList &&!fragmentList.isEmpty())
        {
            for(Fragment fragment : fragmentList)
            {
                transition.remove(fragment);
            }
            transition.commitAllowingStateLoss();
        }
        EasyHelper.inject(this,mParentView, this);
        injectLayoutAfter(mParentView);
        mPresenter = ClassUtil.newSimpleInstance(ClassUtil.getSuperClassGenricType(getClass(), 0));
        mPresenter.setIntent(getIntent());
        mPresenter.onCreate(getApplicationContext());
        mPresenter.setView((IView) this);

        initViews(mSvedInstanceState,mParentView);
        mPresenter.initPeresenter(mSvedInstanceState, mPresenter.getView());

    }




    public void startActivity(Class<? extends Activity> activityClass)
    {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        super.startActivity(intent);
    }


    public P getPresenter() {
        return mPresenter;
    }



    public void startActivityFromLeftToRight(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left_to_right, R.anim.exit_right);
    }

    protected void toastWithSnackbar(View view, String message)
    {
        toastWithSnackbar(view, message, "好的，我知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void toastWithSnackbar(View view, String message, String action, View.OnClickListener onClickListener)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(action, onClickListener).show();
    }


    protected void toast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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



