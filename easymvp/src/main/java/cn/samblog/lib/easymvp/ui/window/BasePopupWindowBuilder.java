package cn.samblog.lib.easymvp.ui.window;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zhy.autolayout.utils.AutoUtils;
import cn.samblog.lib.easymvp.utils.EasyHelper;

/**
 * 窗口构造器
 * @author Sam
 */
public abstract class BasePopupWindowBuilder {

    private     PopupWindow popupWindow;
    private Context context;
    private View windowView;

    protected abstract void init(View windowView);


    protected  abstract int getWith();

    protected   abstract int getHeight();

    public BasePopupWindowBuilder(Context context)
    {
        this.context = context;
        windowView  = EasyHelper.getLayoutView(context, this);

        EasyHelper.injectWindow(this, windowView, context);
        AutoUtils.auto(windowView);
        popupWindow = new PopupWindow(windowView,getWith(),  getHeight());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        init(windowView);

    }

    public Context getWindowContext()
    {
        return context;
    }

    public PopupWindow getWindow()
    {
        return  popupWindow;
    }

    private Window window;

    public void show(View contextView, Window window)
    {
        getWindow().showAtLocation(contextView, Gravity.CENTER, 0, 0);
        this.window = window;
        proccessWindowAlpha(true);
    }


    public void show(View contextView)
    {
        getWindow().showAtLocation(contextView, Gravity.CENTER, 0, 0);
    }

    public void dismiss()
    {
        getWindow().dismiss();
    }

    protected  ValueAnimator alphaValueAnimator;

    protected void proccessWindowAlpha(boolean isShow)
    {
        if(null != alphaValueAnimator)
            alphaValueAnimator.cancel();
        alphaValueAnimator = isShow? ValueAnimator.ofFloat(1, 0.5f):ValueAnimator.ofFloat(0.5f, 1);
        alphaValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha= (float) animation.getAnimatedValue();
                window.setAttributes(lp);
            }
        });
        alphaValueAnimator.setDuration(500);
        alphaValueAnimator.start();
    }


}
