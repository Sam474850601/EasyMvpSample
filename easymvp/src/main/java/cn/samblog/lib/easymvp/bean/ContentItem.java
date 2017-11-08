package cn.samblog.lib.easymvp.bean;

/**
 * 内容数据
 *
 * @author Sam
 */
public final class ContentItem extends Item {
    public ContentItem() {
        super(TYPE_CONTENT);
    }

    public ContentItem(int type) {
        super(type);
    }

    public ContentItem(Object data) {
        super(TYPE_CONTENT, data);
    }
}