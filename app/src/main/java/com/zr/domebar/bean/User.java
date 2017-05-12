package com.zr.domebar.bean;


import cn.bmob.v3.BmobUser;

/**
 * Created by S01 on 2017/5/9.
 */

public class User extends BmobUser {
    private String unamae;
    private String upwd;

    public User() {
    }

    public User(String unamae, String upwd) {
        this.unamae = unamae;
        this.upwd = upwd;
    }

    public String getUnamae() {
        return unamae;
    }

    public void setUnamae(String unamae) {
        this.unamae = unamae;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

}
