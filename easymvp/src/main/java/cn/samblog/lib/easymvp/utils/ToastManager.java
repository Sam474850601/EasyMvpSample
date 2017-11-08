package cn.samblog.lib.easymvp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具
 * @author Sam
 */

public class ToastManager {


    private ToastManager(){}

    private static ToastManager instance;

    public static ToastManager getInstance()
    {
        if(null == instance)
        {
            synchronized (ToastManager.class)
            {
                if(null == instance)
                {
                    return instance = new ToastManager();
                }

            }
        }
        return instance;
    }

    Toast toast;


    public void init(Context context)
    {
        toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
    }

    public void setDuration(int duration)
    {
        toast.setDuration(duration);
    }


    public void show(String message)
    {
        toast.setText(message);
        toast.show();
    }


    public void show(int messageRes)
    {
        toast.setText(messageRes);
        toast.show();
    }


}
