package cn.samblog.easymvp.sample.presenter.other;

import android.os.Bundle;

import cn.samblog.easymvp.sample.ui.view.other.ISecondFragmentView;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

public class SecondFragmentPresenter extends BasePresenter<ISecondFragmentView> implements ISecondFragmentPresenter {



    @Override
    public void loadMessage() {
        ISecondFragmentView view = getView();
        if(null != view)
        {
            view.setSecondMessage("fragment 的 fragment");
        }
    }

    @Override
    public void initPeresenter(Bundle savedInstanceState, ISecondFragmentView view) {

    }
}
