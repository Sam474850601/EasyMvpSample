package cn.samblog.easymvp.sample.presenter.other;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.samblog.easymvp.sample.ui.view.other.IRecyclerViewAdapterDemo1view;
import cn.samblog.lib.easymvp.bean.ContentItem;
import cn.samblog.lib.easymvp.presenter.BasePresenter;

/**
 * Created by Ping on 2017/11/11.
 */

public class RecyclerViewAdapterDemo1Presenter extends BasePresenter<IRecyclerViewAdapterDemo1view> implements IRecyclerViewAdapterDemo1Presenter {


    @Override
    public void loadMore() {
        List<ContentItem> list = new  ArrayList();

        for(int i =0; i < 4; i++ )
        {
            list.add(new ContentItem("设置数据内容"));
        }
        IRecyclerViewAdapterDemo1view view = getView();
        if(null != view)
        {
            view.update(list);
        }
    }

    @Override
    public void initPeresenter(Bundle savedInstanceState, IRecyclerViewAdapterDemo1view view) {
        loadMore();
    }
}
