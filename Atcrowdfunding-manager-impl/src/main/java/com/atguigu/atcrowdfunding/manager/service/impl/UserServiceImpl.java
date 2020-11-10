package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.DataList;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.exception.LoginException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public User queryUser(Map<String, Object> map) throws Exception {
        User user = userMapper.queryUser(map);
        //判断user是否为空
        if(user==null){
            throw new LoginException("登录失败");
        }
        return user;
    }

    @Override
    public int saveUser(User user) {
        int count = userMapper.insert(user);
        if(count!=1){
            throw new RuntimeException("添加失败");
        }
        return count;
    }

    @Override
    public int deleteUser(Integer id) {
        int count= userMapper.deleteByPrimaryKey(id);
        if(count!=1){
            throw new RuntimeException("删除失败");
        }
        return count;
    }

    @Override
    public int deleteUserBatch(DataList dataList) {
        int count=userMapper.deleteUserBatch(dataList.getUserlist());
        if(count!=dataList.getUserlist().size()){
            throw new RuntimeException("删除失败");
        }
        return count;
    }

    @Override
    public User queryUserById(Integer id) {
        User user = userMapper.queryUserById(id);
        if(user==null){
            throw  new RuntimeException("查询失败");
        }
        return user;
    }

    @Override
    public int editUser(User user) {
        int count = userMapper.updateByPrimaryKey(user);
        if(count!=1){
            throw new RuntimeException("修改");
        }
        return count;
    }

    @Override
    public Page queryUserList(Map map) {
        Page page = new Page((Integer)map.get("pageno"),(Integer) map.get("pagesize"));
        //查询用户列表
        Integer startIndex = page.getStartIndex(page.getPageno(),page.getPageszie());
        map.put("startIndex",startIndex);
        List<User> userList = userMapper.queryUserList(map);
        //查询用户数量
        int total = userMapper.queryUserTotal(map);
        //封装到page中
        page.setTotalsize(total);
        page.setUserlist(userList);
        return page;
    }
}
