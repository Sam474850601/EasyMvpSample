package cn.samblog.easymvp.sample.presenter.other;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.samblog.easymvp.sample.ui.view.other.IRecyclerViewAdapterDemoview;
import cn.samblog.lib.easymvp.bean.ContentItem;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * Created by Ping on 2017/11/11.
 */

public class RecyclerViewAdapterDemo1Presenter extends BasePresenter<IRecyclerViewAdapterDemoview> implements IRecyclerViewAdapterDemoPresenter {


    @Override
    public void loadMore() {
        List<ContentItem> list = new  ArrayList();

        for(int i =0; i < 4; i++ )
        {
            //ContentItem的data可以是任何对象，
            list.add(new ContentItem("设置数据内容"));
        }
        IRecyclerViewAdapterDemoview view = getView();
        if(null != view)
        {
            view.update(list);
        }
    }

    @Override
    public void initPeresenter(Bundle savedInstanceState, IRecyclerViewAdapterDemoview view) {
        loadMore();
    }
}
