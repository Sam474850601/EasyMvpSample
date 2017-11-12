package cn.samblog.easymvp.sample.presenter.other;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.samblog.easymvp.sample.bean.Info;
import cn.samblog.easymvp.sample.ui.view.other.IRecyclerViewAdapterDemoview;
import cn.samblog.lib.easymvp.bean.ContentItem;
import cn.samblog.lib.easymvp.bean.FooterItem;
import cn.samblog.lib.easymvp.bean.GroupItem;
import cn.samblog.lib.easymvp.bean.HeaderItem;
import cn.samblog.lib.easymvp.bean.Item;
import cn.samblog.lib.easymvp.presenter.BasePresenter;



public class RecyclerViewAdapterDemo2Presenter extends BasePresenter<IRecyclerViewAdapterDemoview> implements IRecyclerViewAdapterDemoPresenter {


    @Override
    public void loadMore() {

        //可以添加任何数据类型
        List<Item> list = new  ArrayList();

        list.add(new ContentItem("我是ContentItem"));
        list.add(new GroupItem("GroupItem"));

        Info info = new Info();
        info.setMessage("呵呵哒");
        info.setTitle("测试");

        list.add(new HeaderItem(info));



        Info footInfo = new Info();
        footInfo.setMessage("哈哈哈哒");
        footInfo.setTitle("测试");
        list.add(new FooterItem(footInfo));

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
