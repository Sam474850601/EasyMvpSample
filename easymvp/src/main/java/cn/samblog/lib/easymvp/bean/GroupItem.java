package cn.samblog.lib.easymvp.bean;

/**
 * 分组数据
 *
 * @author Sam
 */
public final class GroupItem extends Item {

    public GroupItem() {
        super(TYPE_GROUP);
    }

    public GroupItem(int type) {
        super(type);
    }

    public GroupItem(Object data) {
        super(TYPE_GROUP, data);
    }
}