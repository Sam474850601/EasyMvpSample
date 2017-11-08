package cn.samblog.lib.easymvp.presenter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.samblog.lib.easymvp.ui.view.IView;


/**
 *@author Sam
 */
public interface IPresenter<T extends IView> {
    void onCreate(Context applicationContext);
    void initPeresenter(Bundle savedInstanceState, T view);
    void setView(T view);
    void onResume();
    void onRestart();
    void onPause();
    void onStop();
    void onDestroy();
    void onStart();
    T getView();
    Intent getIntent();
    void setIntent(Intent activityIntent);
}
