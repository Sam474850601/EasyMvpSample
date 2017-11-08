package cn.samblog.lib.easymvp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.bean.Item;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter 基类
 */
public abstract class BaseRecyclerViewAdapter<C extends IHolder,G extends IHolder,H extends IHolder,F extends IHolder> extends RecyclerView.Adapter
        implements OnContentItemClickedListener, OnGroupItemClickedListener, OnFooterItemClickedListener, OnHeaderItemClickedListener
{
    protected final int POSITION_CONTENT = 0;
    protected final int POSITION_GROUP = 1;
    protected final int POSITION_HEADER = 2;
    protected final int POSITION_FOOTER= 3;

    private  List<Item> itemList = new ArrayList<Item>();

    public int countByType(int type)
    {
        synchronized (this) {
            if(null == itemList)
                return 0;
            int count = 0;
            final   int adapterSize = itemList.size();
            for (int i = 0; i < adapterSize; i ++ ) {
                final Item item = itemList.get(i);
                if(item.type  == type)
                    count ++;
            }
            return count;
        }
    }


    public int getContentCount()
    {
        return  countByType(Item.TYPE_CONTENT);
    }

    public int getFooterCount()
    {
        return  countByType(Item.TYPE_FOOTER);
    }

    public int getHeaderCount()
    {
        return  countByType(Item.TYPE_HEADER);
    }
    public int getGroupCount()
    {
        return  countByType(Item.TYPE_GROUP);
    }


    public void addItem(Item item)
    {
        synchronized (this)
        {
            getItemList().add(item);
        }
    }

    public void addItems(List<Item> items)
    {
        synchronized (this)
        {
            getItemList().addAll(items);
        }
    }



    /**
     * 设置列表item
     * @param itemList
     */
    public void setItemList(List<Item> itemList)
    {
        if(null == itemList)
            return;
        this.itemList = itemList;
    }

    /**
     * 获取列表item
     * @return
     */
    public List<Item> getItemList()
    {
        return  itemList;
    }

    private abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        private IHolder viewContent;
        public IHolder holderInstance;
        protected abstract Class supportViewHolder();

        public BaseViewHolder(View itemView)  {
            super(itemView);
            Class<IHolder> holderClass = supportViewHolder();
            if (!holderClass.isAssignableFrom(Null.class)) {
                Constructor<?> constructor = holderClass.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                try {
                    holderInstance = (IHolder) constructor.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException("cant not new instance for "+getClass().getSimpleName());
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("cant not new instance for "+getClass().getSimpleName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Field[] fields = holderClass.getDeclaredFields();
                if (null != fields) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Find.class)) {
                            Class classtype = field.getType();
                            if(!classtype.isPrimitive())
                            {
                                Find find = field.getAnnotation(Find.class);
                                if(View.class.isAssignableFrom(classtype))
                                {
                                    int resourceId = find.value();
                                    View view= null;
                                    try {
                                        view = itemView.findViewById(resourceId);
                                        field.set(holderInstance, view);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                    }
                }
            }

        }

    }

    /**
     * 获取对应位置泛型的class
     * @param postion
     * @return
     */
    protected    Class getSupportClass(int postion)
    {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Null.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(params[postion] instanceof Class)) {
            return Null.class;
        }
        return (Class) params[postion];
    }


    private class HeaderViewHolder extends BaseViewHolder implements View.OnClickListener , View.OnLongClickListener{

        public HeaderViewHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onHeaderClicked(v, getPosition(), itemList.get(getPosition()), false);
        }
        @Override
        protected Class supportViewHolder() {


            return getSupportClass(POSITION_HEADER);
        }

        @Override
        public boolean onLongClick(View v) {
            onHeaderClicked(v, getPosition(), itemList.get(getPosition()), true);
            return false;
        }
    }


    private class FooterViewHolder extends BaseViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public FooterViewHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFooterClicked(v, getPosition(), itemList.get(getPosition()), false);
        }



        @Override
        protected Class supportViewHolder() {
            return getSupportClass(POSITION_FOOTER);
        }

        @Override
        public boolean onLongClick(View v) {
            onFooterClicked(v, getPosition(), itemList.get(getPosition()), true);
            return false;
        }




    }

    private class ContentViewHolder extends BaseViewHolder  implements View.OnClickListener, View.OnLongClickListener{

        public ContentViewHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContentClicked(v, getPosition(), itemList.get(getPosition()), false);
        }

        @Override
        protected Class supportViewHolder() {
            return getSupportClass(POSITION_CONTENT);
        }

        @Override
        public boolean onLongClick(View v) {
            onContentClicked(v, getPosition(), itemList.get(getPosition()), true);
            return false;
        }
    }


    private class GroupHolder extends BaseViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public GroupHolder(View itemView) throws IllegalAccessException, InstantiationException {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        protected Class supportViewHolder() {
            return getSupportClass(POSITION_GROUP);
        }

        @Override
        public void onClick(View v) {
            onGroupClicked(v, getPosition(), itemList.get(getPosition()), false);
        }

        @Override
        public boolean onLongClick(View v) {
            onGroupClicked(v, getPosition(), itemList.get(getPosition()), true);
            return false;
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        try {
            switch (type) {
                case Item.TYPE_CONTENT: {
                    Class holderClass = getSupportClass(POSITION_CONTENT);
                    Resource resource = (Resource) holderClass.getAnnotation(Resource.class);
                    int layout = resource.layoutResource();
                    View convertView =  LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
                    AutoUtils.auto(convertView);
                    return  new ContentViewHolder(convertView);
                }
                case Item.TYPE_HEADER: {
                    Class holderClass = getSupportClass(POSITION_HEADER);
                    Resource resource = (Resource) holderClass.getAnnotation(Resource.class);
                    View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(resource.layoutResource(), viewGroup, false);
                    AutoUtils.auto(convertView);
                    return new HeaderViewHolder(convertView);

                }

                case Item.TYPE_GROUP: {
                    Class holderClass = getSupportClass(POSITION_GROUP);
                    Resource resource = (Resource) holderClass.getAnnotation(Resource.class);
                    View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(resource.layoutResource(), viewGroup, false);
                    AutoUtils.auto(convertView);
                    return new GroupHolder(convertView);
                }

                case Item.TYPE_FOOTER: {
                    Class holderClass = getSupportClass(POSITION_FOOTER);
                    Resource resource = (Resource) holderClass.getAnnotation(Resource.class);
                    View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(resource.layoutResource(), viewGroup, false);
                    AutoUtils.auto(convertView);
                    return new FooterViewHolder(convertView);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    protected abstract  void onContentUpdate( C holder, int position, Item item);


    protected   void onHeaderUpdate(H holder, int position, Item item) {

    }


    protected    void onFooterUpdate(F holder, int position, Item item) {

    }


    protected  void onGroupUpdate(G holder, int position, Item item) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Item item = itemList.get(i);
        BaseViewHolder holder = (BaseViewHolder) viewHolder;
        switch (item.type) {
            case Item.TYPE_CONTENT: {
                onContentUpdate((C) holder.holderInstance,  i, item);
            }
            break;
            case Item.TYPE_GROUP: {
                onGroupUpdate((G) holder.holderInstance, i, item);
            }
            break;
            case Item.TYPE_HEADER: {
                onHeaderUpdate((H) holder.holderInstance, i, item);
            }
            break;
            case Item.TYPE_FOOTER: {
                onFooterUpdate((F) holder.holderInstance, i, item);
            }
            break;
        }




    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void onContentClicked(View view, int position, Item item, boolean isLongClick) {

    }

    @Override
    public void onGroupClicked(View view, int position, Item item, boolean isLongClick) {

    }

    @Override
    public void onHeaderClicked(View view, int position, Item item, boolean isLongClick) {

    }

    @Override
    public void onFooterClicked(View view, int position, Item item, boolean isLongClick) {

    }
}
