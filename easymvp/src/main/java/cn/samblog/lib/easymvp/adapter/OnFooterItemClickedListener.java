package cn.samblog.lib.easymvp.adapter;

import android.view.View;

import cn.samblog.lib.easymvp.bean.Item;

public interface OnFooterItemClickedListener
{
    void onFooterClicked(View view, int position, Item item, boolean isLongClick);
}