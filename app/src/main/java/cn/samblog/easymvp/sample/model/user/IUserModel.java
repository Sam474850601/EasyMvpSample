package cn.samblog.easymvp.sample.model.user;

import cn.samblog.easymvp.sample.bean.User;

/**
 * Created by Administrator on 2017/11/8.
 */

public interface IUserModel {

    void setUserName(String userName);
    String getUserName();

    void saveUser(User user);
    User getCacheUser();
}
