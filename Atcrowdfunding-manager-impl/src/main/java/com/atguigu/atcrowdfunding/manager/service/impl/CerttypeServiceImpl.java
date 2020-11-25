package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.AccountTypeCert;
import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.dao.AccountTypeCertMapper;
import com.atguigu.atcrowdfunding.manager.dao.CertMapper;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CerttypeServiceImpl implements CerttypeService {
    @Resource
    private CertMapper certMapper;
    @Resource
    private AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public List<Cert> queryAllCert() {
        return certMapper.queryAllCert();
    }

    @Override
    public List<AccountTypeCert> queryAccountTypeCert() {
        return accountTypeCertMapper.queryAccountTypeCert();
    }

    @Override
    public void insertAccountTypeCert(Integer certid, String accttype) {
        accountTypeCertMapper.insertAccountTypeCert(certid,accttype);
    }

    @Override
    public void deleteAccountTypeCert(Integer certid, String accttype) {
        accountTypeCertMapper.deleteAccountTypeCert(certid,accttype);
    }

    @Override
    public List<AccountTypeCert> queryAccountTypeCertByAccttype(String accttype) {
        return accountTypeCertMapper.queryAccountTypeCertByAccttype(accttype);
    }

    @Override
    public List<Cert> queryCertByid(List<Integer> certid) {
        return certMapper.queryCertByid(certid);
    }
}
