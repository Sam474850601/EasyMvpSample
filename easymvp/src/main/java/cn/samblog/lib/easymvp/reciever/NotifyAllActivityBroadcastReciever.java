package cn.samblog.lib.easymvp.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * 通知所有Activity的广播
 * @author Sam
 */
public class NotifyAllActivityBroadcastReciever extends BroadcastReceiver{

    public static final String ACTION = "com.zkzk365.yoli.reciever.NotifyAllActivityBroadcastReciever";


    public static final String MESSAGE = "message";

    public static final String PARAM_DATA = "data";

    public static final String DATA_CLOSE_ACTIVITY = "close_activity";


    private NotifyAllActivityBroadcastReciever(){}
    public static NotifyAllActivityBroadcastReciever newInstance(Context appContext)
    {
        NotifyAllActivityBroadcastReciever activityCommunicationManager = new NotifyAllActivityBroadcastReciever();
        IntentFilter intentFilter = new IntentFilter(ACTION);
        intentFilter.setPriority(1000);
        appContext.registerReceiver(activityCommunicationManager, intentFilter);
        return activityCommunicationManager;
    }

    public void release(Context context)
    {
        if(null != onRecieveListenerWeakReference)
        {
            onRecieveListenerWeakReference.clear();
            onRecieveListenerWeakReference = null;
            System.runFinalization();
            System.gc();
        }
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION.equals(intent.getAction()))
        {
            if(null != onRecieveListenerWeakReference)
            {
                OnRecieveListener onRecieveListener =   onRecieveListenerWeakReference.get();
                if(null != onRecieveListener)
                {
                    onRecieveListener.onRecieve(intent);
                }
            }
        }
    }


    public static  void notifyExpiration(Context context, String message)
    {
        Intent intent  = new Intent(ACTION);
        intent.putExtra(MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static  void notifyCloseActivities(Context context)
    {
        Intent intent  = new Intent(ACTION);
        intent.putExtra(PARAM_DATA, DATA_CLOSE_ACTIVITY);
        context.sendBroadcast(intent);
    }

    public static  void notifyExpiration(Context context, String bundleName, Bundle bundle)
    {
        Intent intent  = new Intent(ACTION);
        intent.putExtra(bundleName, bundle);
        context.sendBroadcast(intent);
    }


    public interface OnRecieveListener
    {
        //接到消息回调
        void onRecieve(Intent intent);
    }


    private WeakReference<OnRecieveListener> onRecieveListenerWeakReference;

    public void setOnRecieveListener(OnRecieveListener onRecieveListener) {
        this.onRecieveListenerWeakReference = new WeakReference<OnRecieveListener>(onRecieveListener);
    }
}
