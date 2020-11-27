package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.User;

import java.util.ArrayList;
import java.util.List;

public class DataList {
    private List<User> userlist = new ArrayList<>();
    private List<Integer> ids;
    private List<MemberCert> certImgs = new ArrayList<>();

    public List<MemberCert> getCertImgs() {
        return certImgs;
    }

    public void setCertImgs(List<MemberCert> certImgs) {
        this.certImgs = certImgs;
    }

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
