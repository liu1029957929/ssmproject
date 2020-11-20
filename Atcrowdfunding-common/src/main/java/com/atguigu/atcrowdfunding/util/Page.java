package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class Page {
    private Integer pageno;
    private Integer pagesize;
    private Integer totalno;
    private Integer totalsize;
    private List<User> userlist;
    private List<Role> roleList;
    private List<Advertisement> advertList;
    private List data;

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public List<Advertisement> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advertisement> advertList) {
        this.advertList = advertList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }

    public Page(Integer pageno, Integer pagesize){
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPageszie() {
        return pagesize;
    }

    public Integer getTotalno() {
        return totalno;
    }

    private void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }

    public Integer getTotalsize() {
        return totalsize;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    //通过这个方法可以获得总的页数
    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
        this.totalno=(totalsize%pagesize==0)?(totalsize/pagesize):(totalsize/pagesize+1);
    }

    //获取需要查询的user的首序号
    public Integer getStartIndex(Integer pageno,Integer pagesize){
        return (pageno-1)*pagesize;
    }
}
