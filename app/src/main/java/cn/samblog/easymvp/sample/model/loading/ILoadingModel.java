package cn.samblog.easymvp.sample.model.loading;

import cn.samblog.easymvp.sample.model.loading.callback.ILoadingCallback;

/**
 * Created by Administrator on 2017/11/9.
 */

public interface ILoadingModel {
        void loading(ILoadingCallback callback);
}

