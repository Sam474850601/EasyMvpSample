package cn.samblog.easymvp.sample;

import android.app.Application;

import cn.samblog.lib.easymvp.utils.OrmCache;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OrmCache.getInstance().init(getApplicationContext());
    }
}
