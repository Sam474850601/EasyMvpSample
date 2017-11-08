package cn.samblog.lib.easymvp.ui.fragment;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Activity快速开发基类
 *
 * @author Sam
 */
public abstract class BaseFragment<T extends Activity> extends BaseChildFragment {

    public T getBaseActivity() {
        return mActivityWRF.get();
    }

    protected WeakReference<T> mActivityWRF;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityWRF = new WeakReference<T>((T) context);
    }

    @Override
    public void onDestroy() {
        if(null != mActivityWRF)
        {
            mActivityWRF.clear();
            System.runFinalization();
            System.gc();
        }
        super.onDestroy();
    }
}
