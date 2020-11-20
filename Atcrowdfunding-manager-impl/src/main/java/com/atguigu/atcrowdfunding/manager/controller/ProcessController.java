package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping("/index")
    public String index(){
        return "process/index";
    }

    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize,
                          String searchCondition) {
        AjaxResult result = new AjaxResult();
        try {
            Page page = new Page(pageno,pagesize);
            RepositoryService repositoryService = processEngine.getRepositoryService();
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
            List<ProcessDefinition> processDefinitionList = query.listPage(page.getStartIndex(pageno,pagesize),pagesize);
            List<Map<String,Object>> myList = new ArrayList<>();
            for(ProcessDefinition processDefinition:processDefinitionList){
                Map<String,Object> map = new HashMap<>();
                map.put("name",processDefinition.getName());
                map.put("version",processDefinition.getVersion());
                map.put("key",processDefinition.getKey());
                myList.add(map);
            }
            page.setData(myList);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }
}
