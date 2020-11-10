package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.User;

import java.util.ArrayList;
import java.util.List;

public class DataList {
    private List<User> userlist = new ArrayList<>();

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
}
