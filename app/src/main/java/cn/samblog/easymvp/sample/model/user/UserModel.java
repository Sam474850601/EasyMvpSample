package cn.samblog.easymvp.sample.model.user;

import cn.samblog.easymvp.sample.bean.User;
import cn.samblog.easymvp.sample.model.user.IUserModel;
import cn.samblog.lib.easymvp.model.ContextModel;
import cn.samblog.lib.easymvp.utils.OrmCache;

/**
 * Created by Administrator on 2017/11/8.
 */

public class UserModel extends ContextModel  implements IUserModel {
    private  final  User user = new User();


    @Override
    public void setUserName(String userName) {
        user.setUsername(userName);
    }

    @Override
    public String getUserName() {
        return user.getUsername();
    }

    @Override
    public void saveUser(User user) {
        //持久化保存到手机，只保存@CacheField注解的字段
        OrmCache.getInstance().save(user);
    }

    @Override
    public User getCacheUser() {
        //从手机上拿取
        return OrmCache.getInstance().getCache(User.class);
    }
}
