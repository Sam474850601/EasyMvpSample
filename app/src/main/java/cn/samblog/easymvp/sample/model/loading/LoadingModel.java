package cn.samblog.easymvp.sample.model.loading;

import android.content.Context;
import android.os.Handler;

import cn.samblog.easymvp.sample.model.loading.callback.ILoadingCallback;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.model.ContextModel;

/**
 * Created by Administrator on 2017/11/9.
 */

public class LoadingModel extends ContextModel implements ILoadingModel {

    @Inject
    Handler handler;


    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
    }

    @Override
    public void loading(final ILoadingCallback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted();
            }
        }, 3000);
    }
}
