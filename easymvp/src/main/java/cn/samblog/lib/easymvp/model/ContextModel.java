package cn.samblog.lib.easymvp.model;

import android.content.Context;

import cn.samblog.lib.easymvp.utils.EasyHelper;

import java.lang.ref.WeakReference;

/**
 * @author Sam
 */
public abstract class ContextModel {
    WeakReference<Context> weakReference;
    protected   void onCreate(Context appContext){
        weakReference = new WeakReference<Context>(appContext);
        EasyHelper.injectMode(this, appContext);
    }
    private  final   void _onCreate(Context context)
    {
        onCreate(context);
    }

    public Context  getContext()
    {
        return  weakReference.get();
    }

}
