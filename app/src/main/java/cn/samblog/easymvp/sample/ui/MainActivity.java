package cn.samblog.easymvp.sample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.samblog.easymvp.sample.R;
import cn.samblog.lib.easymvp.annotation.Presenter;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
