package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.AccountTypeCert;
import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.DataList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/certtype")
public class CerttypeController {
    @Resource
    private CerttypeService certtypeService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        List<Cert> certList= certtypeService.queryAllCert();
        //查询数据
        List<AccountTypeCert> accountTypeCertList = certtypeService.queryAccountTypeCert();
        request.setAttribute("accountTypeCertList",accountTypeCertList);
        request.setAttribute("certList",certList);
        return "certtype/index";
    }


    @ResponseBody
    @RequestMapping("/insertAccountTypeCert")
    public Object insertAccountTypeCert(Integer certid, String accttype) {
        AjaxResult result = new AjaxResult();
        try {
            certtypeService.insertAccountTypeCert(certid,accttype);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteAccountTypeCert")
    public Object deleteAccountTypeCert(Integer certid, String accttype) {
        AjaxResult result = new AjaxResult();
        try {
            certtypeService.deleteAccountTypeCert(certid,accttype);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }
}
