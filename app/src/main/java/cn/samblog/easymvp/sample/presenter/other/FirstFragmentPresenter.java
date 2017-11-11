package cn.samblog.easymvp.sample.presenter.other;

import android.os.Bundle;

import cn.samblog.easymvp.sample.ui.view.other.ISimpleFragmentView;
import cn.samblog.lib.easymvp.presenter.BasePresenter;
import cn.samblog.lib.easymvp.ui.view.IView;


public class FirstFragmentPresenter extends BasePresenter<ISimpleFragmentView> implements IFirstFragmentPresenter {


    @Override
    public void loadMessage() {
        ISimpleFragmentView view = getView();
        if(null != view)
        {
            view.setFirstViewMessage("Activity的FirstFragment");
        }
    }

    @Override
    public void initPeresenter(Bundle savedInstanceState, ISimpleFragmentView view) {

    }
}
