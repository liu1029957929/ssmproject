package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.bean.UserExample;
import com.atguigu.atcrowdfunding.util.DataList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryUser(Map<String, Object> map);

    //List<User> queryUserList(@Param("startIndex")Integer startIndex, @Param("pagesize")Integer pagesize);

    //int queryUserTotal();

    List<User> queryUserList(Map map);

    int queryUserTotal(Map map);

    int deleteUserBatch(List userList);

    User queryUserById(Integer id);
}