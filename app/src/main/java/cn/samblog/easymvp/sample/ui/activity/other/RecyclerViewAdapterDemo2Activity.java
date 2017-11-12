package cn.samblog.easymvp.sample.ui.activity.other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.bean.Info;
import cn.samblog.easymvp.sample.presenter.other.IRecyclerViewAdapterDemoPresenter;
import cn.samblog.easymvp.sample.presenter.other.RecyclerViewAdapterDemo2Presenter;
import cn.samblog.easymvp.sample.ui.view.other.IRecyclerViewAdapterDemoview;
import cn.samblog.lib.easymvp.adapter.BaseRecyclerViewAdapter;
import cn.samblog.lib.easymvp.adapter.IHolder;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Inject;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Presenter;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.bean.Item;
import cn.samblog.lib.easymvp.ui.activity.BaseActivity;

/**
 *  万能RecyclerViewAdapte 多类型布局演示。
 *
 *
 * @author Sam
 */
@Resource(layoutResource = R.layout.activity_recyclerviewadapterdemo)
public class RecyclerViewAdapterDemo2Activity extends BaseActivity implements IRecyclerViewAdapterDemoview
{
    @Find(R.id.list)
    RecyclerView recyclerView;

    @Inject
    MyAdapter myAdapter;


    @Presenter(RecyclerViewAdapterDemo2Presenter.class)
    IRecyclerViewAdapterDemoPresenter presenter;


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
    public void update(List<? extends Item> items) {
        myAdapter.addItems(items);
        myAdapter.notifyDataSetChanged();
    }


    @Resource(layoutResource = R.layout.adapter_style1)
    public static class ContentHodler implements IHolder
    {
        @Find(R.id.tv_message)
        TextView tvMessage;

    }

    @Resource(layoutResource = R.layout.adapter_style2)
    public static class GroupHodler implements IHolder
    {
        @Find(R.id.tv_message)
        TextView tvMessage;

    }

    @Resource(layoutResource = R.layout.adapter_style3)
    public static class FootHodler implements IHolder
    {
        @Find(R.id.tv_message)
        TextView tvMessage;

        @Find(R.id.tv_title)
        TextView tvTitle;

    }

    @Resource(layoutResource = R.layout.adapter_style4)
    public static class HeaderHodler implements IHolder
    {
        @Find(R.id.tv_message)
        TextView tvMessage;

        @Find(R.id.tv_title)
        TextView tvTitle;

    }




    public static class MyAdapter extends BaseRecyclerViewAdapter<ContentHodler,GroupHodler, HeaderHodler, FootHodler>
    {

        @Override
        protected void onContentViewPrepare(View view) {
            // 如果你的Activity是AutoActivity可以在这设置AutoUtils.auto(view)
        }

        @Override
        protected void onContentUpdate(ContentHodler holder, int position, Item item) {
            String text = item.getData();
            holder.tvMessage.setText("onContentUpdate测试："+position+", text"+text);
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


        @Override
        protected void onFooterUpdate(FootHodler holder, int position, Item item) {
            Info info = item.getData();
            holder.tvMessage.setText(info.getMessage());
            holder.tvTitle.setText(info.getTitle());
        }

        @Override
        public void onFooterClicked(View view, int position, Item item, boolean isLongClick) {
            Toast.makeText(view.getContext(), "onFooterClicked",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onGroupUpdate(GroupHodler holder, int position, Item item) {
            String text = item.getData();
            holder.tvMessage.setText("onGroupUpdate测试："+position+", text"+text);
        }

        @Override
        public void onGroupClicked(View view, int position, Item item, boolean isLongClick) {
            Toast.makeText(view.getContext(), "onGroupClicked",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onHeaderUpdate(HeaderHodler holder, int position, Item item) {
            Info info = item.getData();
            holder.tvMessage.setText(info.getMessage());
            holder.tvTitle.setText(info.getTitle());
        }

        @Override
        public void onHeaderClicked(View view, int position, Item item, boolean isLongClick) {
            Toast.makeText(view.getContext(), "onGroupClicked",Toast.LENGTH_SHORT).show();
        }
    }



}
