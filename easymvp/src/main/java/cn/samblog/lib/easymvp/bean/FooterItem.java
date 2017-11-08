package cn.samblog.lib.easymvp.bean;

/**
 * 尾部数据
 *
 * @author Sam
 */
public final class FooterItem extends Item {

    public FooterItem() {
        super(TYPE_FOOTER);
    }

    public FooterItem(int type) {
        super(type);
    }

    public FooterItem(Object data) {
        super(TYPE_FOOTER, data);
    }
}