package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.DataList;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UserService {

    User queryUser(Map<String, Object> map) throws Exception;

    Page  queryUserList(Map<String, Object> map);

    int saveUser(User user);

    int deleteUser(Integer id);

    int deleteUserBatch(DataList dataList);

    User queryUserById(Integer id);

    int editUser(User user);

    List<Role> queryAllRole();

    List<Integer> queryRoleId(Integer id);

    void saveUserRoleRelationship(Integer userid, DataList data);

    void deleteUserRoleRelationship(Integer userid, DataList data);

    //Page queryUserList(Integer pageno, Integer pagesize);

}
