package cn.samblog.easymvp.sample.ui.view.other;

import java.util.List;

import cn.samblog.lib.easymvp.bean.Item;
import cn.samblog.lib.easymvp.ui.view.IView;

/**
 * @author Sam
 */

public interface IRecyclerViewAdapterDemoview extends IView{

    void update(List<? extends Item> items);

}
