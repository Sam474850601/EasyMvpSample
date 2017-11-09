package cn.samblog.easymvp.sample.model.user;

import cn.samblog.easymvp.sample.bean.User;
import cn.samblog.easymvp.sample.model.user.IUserModel;
import cn.samblog.lib.easymvp.model.ContextModel;

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
}
