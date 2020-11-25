package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.AccountTypeCert;
import com.atguigu.atcrowdfunding.bean.AccountTypeCertExample;
import com.atguigu.atcrowdfunding.bean.Cert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountTypeCertMapper {
    long countByExample(AccountTypeCertExample example);

    int deleteByExample(AccountTypeCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    int insertSelective(AccountTypeCert record);

    List<AccountTypeCert> selectByExample(AccountTypeCertExample example);

    AccountTypeCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountTypeCert record, @Param("example") AccountTypeCertExample example);

    int updateByExample(@Param("record") AccountTypeCert record, @Param("example") AccountTypeCertExample example);

    int updateByPrimaryKeySelective(AccountTypeCert record);

    int updateByPrimaryKey(AccountTypeCert record);

    List<AccountTypeCert> queryAccountTypeCert();

    void insertAccountTypeCert(@Param("certid")Integer certid, @Param("accttype")String accttype);

    void deleteAccountTypeCert(@Param("certid")Integer certid, @Param("accttype")String accttype);

    List<AccountTypeCert> queryAccountTypeCertByAccttype(String accttype);
}