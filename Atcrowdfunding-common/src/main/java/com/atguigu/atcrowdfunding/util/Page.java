package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.User;

import java.util.List;

public class Page {
    private Integer pageno;
    private Integer pagesize;
    private Integer totalno;
    private Integer totalsize;
    private List<User> userlist;

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

    public void setPageszie(Integer pagesize) {
        this.pagesize = pagesize;
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

    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
        this.totalno=(totalsize%pagesize==0)?(totalsize/pagesize):(totalsize/pagesize+1);
    }

    //获取需要查询的user的首序号
    public Integer getStartIndex(Integer pageno,Integer pagesize){
        return (pageno-1)*pagesize;
    }
}
