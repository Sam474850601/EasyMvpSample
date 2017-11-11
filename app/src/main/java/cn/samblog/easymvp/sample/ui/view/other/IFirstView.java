package cn.samblog.easymvp.sample.ui.view.other;

import cn.samblog.lib.easymvp.ui.view.IView;

/**
 * activity 的 Fragment 的View
 */

public interface IFirstView extends ISecondFragmentView{
    void setFirstViewMessage(String message);
}
