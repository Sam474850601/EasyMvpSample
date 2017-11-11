package cn.samblog.easymvp.sample.ui.view.other;

import java.util.List;

import cn.samblog.lib.easymvp.bean.ContentItem;
import cn.samblog.lib.easymvp.ui.view.IView;

/**
 * @author Sam
 */

public interface IRecyclerViewAdapterDemo1view extends IView{

    void update(List<ContentItem> items);

}
