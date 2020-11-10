package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.User;

import java.util.ArrayList;
import java.util.List;

public class DataList {
    private List<User> userlist = new ArrayList<>();
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
}
