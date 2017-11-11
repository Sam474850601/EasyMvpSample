package cn.samblog.lib.easymvp.model;

import android.content.Context;

import cn.samblog.lib.easymvp.utils.EasyHelper;

import java.lang.ref.WeakReference;

/**
 * @author Sam
 */
public abstract class ContextModel {
    WeakReference<Context> weakReference;
    protected   void onCreate(Context context){
        weakReference = new WeakReference<Context>(context);
        EasyHelper.injectMode(this, context);
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
