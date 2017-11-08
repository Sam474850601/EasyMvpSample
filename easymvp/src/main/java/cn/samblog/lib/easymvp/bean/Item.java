package cn.samblog.lib.easymvp.bean;

/**
 * 保存列数据
 */
public class Item {

    /**
     * 保存的数据
     */
    public Object data;

    /**
     * 头部
     */
    public static final int TYPE_HEADER = 0x01;

    /**
     * 尾部
     */
    public static final int TYPE_FOOTER = 0x02;


    /**
     * 内容详情
     */
    public static final int TYPE_CONTENT = 0x03;


    /**
     * 分组
     */
    public static final int TYPE_GROUP = 0x04;

    public int type=TYPE_CONTENT;

    public Item(int type) {
        this.type = type;
    }

    public Item(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public <T> T getData()
    {
        if(null == data)
            return null;
        return (T) data;
    }

}
