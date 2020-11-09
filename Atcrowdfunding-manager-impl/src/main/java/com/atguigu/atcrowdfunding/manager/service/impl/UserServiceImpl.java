package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
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
