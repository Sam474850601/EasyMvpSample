package cn.samblog.lib.easymvp.presenter;


import android.content.Context;
import android.content.Intent;

import cn.samblog.lib.easymvp.ui.view.IView;
import cn.samblog.lib.easymvp.utils.EasyHelper;

import java.lang.ref.WeakReference;

/**
 *@author Sam
 */
public  abstract  class BasePresenter<T extends IView> implements IPresenter<T> {

    private WeakReference<T> mView;

    private WeakReference<Context> contextWeakReference;

    @Override
    public void onCreate(Context applicationContext) {
        EasyHelper.inject(this, null, applicationContext, null);
        contextWeakReference = new WeakReference<Context>(applicationContext);
    }



    @Override
    public void setView(T view) {
        mView = new WeakReference<T>( view);
    }

    @Override
    public T getView()
    {
        return mView.get();
    }

    protected  Context getAppContext()
    {
        return contextWeakReference.get();
    }


    @Override
    public  void onDestroy(){
        mView.clear();
        contextWeakReference.clear();
        System.runFinalization();
        System.gc();
        EasyHelper.release(this);
    }

    @Override
    public void onStop(){

    }

    @Override
    public void onResume(){

    }

    @Override
    public void onRestart(){

    }

    @Override
    public void onPause(){

    }

    @Override
    public void onStart()
    {

    }

    Intent intent;
    @Override
    public  Intent getIntent()
    {
        return intent;
    }

    @Override
    public  void setIntent(Intent activityIntent)
    {
        this.intent = activityIntent;
    }

    public <T> T  getBeanByIntent(Class<T> classType)
    {
        return EasyHelper.getBeanByIntent(classType , getIntent());
    }

    public void  setIntentByBean(Intent intent, Object pojo)
    {
        EasyHelper.setIntentByBean(intent, pojo);
    }
}


