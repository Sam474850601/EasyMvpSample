package cn.samblog.lib.easymvp.ui.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.zhy.autolayout.AutoFrameLayout;

/**
 * A LayoutDialog that you can use it to show in your custom layout
 * @author Sam
 */
public class LayoutDialog extends AutoFrameLayout implements ValueAnimator.AnimatorUpdateListener{

    View vMask;

    public LayoutDialog(Context context, AttributeSet attrs) {
        super(context, attrs);

        vMask = new View(context);
        vMask.setBackgroundColor(Color.parseColor("#80000000"));
        addView(vMask);
        ViewHelper.setAlpha(vMask, 0);
    }

    private boolean isShowing;


    public boolean isShowing() {
        return isShowing;
    }

    private ValueAnimator valueAnimator;


  final   Animator.AnimatorListener linster =  new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    public void show()
    {
        synchronized (this)
        {
            isShowing = true;
            if(null != valueAnimator && valueAnimator.isRunning())
                valueAnimator.cancel();
            if(null == valueAnimator)
            {
                valueAnimator  = ValueAnimator.ofFloat(0f, 5f);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(this);
                valueAnimator.addListener(linster);
            }

            valueAnimator.start();
        }

    }


    public void dismiss()
    {
       synchronized (this)
       {
           isShowing = false;
           ViewHelper.setAlpha(vMask, 0);
           setVisibility(GONE);
       }
    }





    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        ViewHelper.setAlpha(vMask, (Float) animation.getAnimatedValue());
    }


}
