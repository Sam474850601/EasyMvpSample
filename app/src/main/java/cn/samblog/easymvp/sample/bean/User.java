package cn.samblog.easymvp.sample.bean;

import cn.samblog.lib.easymvp.annotation.CacheField;


public class User {
    @CacheField
    public String username = "unknown";

    @CacheField
    public  int age = 0;

    @CacheField
    public  int id = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
