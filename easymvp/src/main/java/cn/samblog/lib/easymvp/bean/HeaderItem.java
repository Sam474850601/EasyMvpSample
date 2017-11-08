package cn.samblog.lib.easymvp.bean;

/**
 * 头部数据
 *
 * @author Sam
 */
public final class HeaderItem extends Item {

    public HeaderItem() {
        super(TYPE_HEADER);
    }

    public HeaderItem(Object data) {
        super(TYPE_HEADER, data);
    }


    public HeaderItem(int type) {
        super(type);
    }
}