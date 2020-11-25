package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.AccountTypeCert;
import com.atguigu.atcrowdfunding.bean.Cert;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CerttypeService {
    List<Cert> queryAllCert();

    List<AccountTypeCert> queryAccountTypeCert();

    void insertAccountTypeCert(Integer certid,String accttype);

    void deleteAccountTypeCert(Integer certid, String accttype);

    List<AccountTypeCert> queryAccountTypeCertByAccttype(String accttype);

    List<Cert> queryCertByid(List<Integer> certid);
}
