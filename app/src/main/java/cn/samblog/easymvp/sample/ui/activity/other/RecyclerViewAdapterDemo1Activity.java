package cn.samblog.easymvp.sample.ui.activity.other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.other.IRecyclerViewAdapterDemo1Presenter;
import cn.samblog.easymvp.sample.presenter.other.RecyclerViewAdapterDemo1Presenter;
import cn.samblog.easymvp.sample.ui.view.other.IRecyclerViewAdapterDemo1view;
import cn.samblog.lib.easymvp.adapter.BaseRecyclerViewAdapter;
import cn.samblog.lib.easymvp.adapter.IHolder;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.bean.ContentItem;
import cn.samblog.lib.easymvp.bean.Item;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;

/**
 * 一个Item布局的RecyclerView
 * @author Sam
 */
@Resource(layoutResource = R.layout.activity_recyclerviewadapterdemo)
public class RecyclerViewAdapterDemo1Activity extends BaseActivity implements IRecyclerViewAdapterDemo1view
{
    @Find(R.id.list)
    RecyclerView recyclerView;

    @Inject
    MyAdapter myAdapter;


    @Presenter(RecyclerViewAdapterDemo1Presenter.class)
    IRecyclerViewAdapterDemo1Presenter presenter;


    @OnClicked(R.id.btn_add)
    void onAddCliked(View btn_add)
    {
        presenter.loadMore();
    }


    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void update(List<ContentItem> items) {
        myAdapter.addItems(items);
        myAdapter.notifyDataSetChanged();
    }



    @Resource(layoutResource = R.layout.adapter_style1)
    public static class ContentHodler implements IHolder
    {
        @Find(R.id.message)
        TextView tvMessage;

    }


    public static class MyAdapter extends BaseRecyclerViewAdapter<ContentHodler, ContentHodler, ContentHodler, ContentHodler>
    {

        @Override
        protected void onContentViewPrepare(View view) {
            // 如果你的Activity是AutoActivity可以在这设置AutoUtils.auto(view)
        }

        @Override
        protected void onContentUpdate(ContentHodler holder, int position, Item item) {
            String text = item.getData();
            holder.tvMessage.setText("测试："+position+", text"+text);
        }

        @Override
        public void onContentClicked(View view, int position, Item item, boolean isLongClick) {
            String text = "操作方式：";
            if(isLongClick)
            {
                text+="长按";
            }
            else
            {
                text+="点击";
            }
            String data= item.getData();
            Toast.makeText(view.getContext(), text+"数据:"+data,Toast.LENGTH_SHORT).show();
        }
    }



}
