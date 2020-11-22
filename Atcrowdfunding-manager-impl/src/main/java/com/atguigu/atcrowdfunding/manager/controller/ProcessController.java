package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


    @RequestMapping("/showimg")
    public String showimg(){
        return "process/showimg";
    }

    @RequestMapping("/doShowImg")
    public void doShowImg(String id, HttpServletResponse response) throws IOException {
        ProcessDefinition processDefinition  = processEngine.getRepositoryService()
                .createProcessDefinitionQuery().processDefinitionId(id).singleResult();
        String deploymentId = processDefinition.getDeploymentId();
        String imgName = processDefinition.getDiagramResourceName();
        InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId,imgName);
        OutputStream out = response.getOutputStream();
        IOUtils.copy(in,out);
    }


    @ResponseBody
    @RequestMapping("/addProDef")
    public Object addProDef(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        try {
            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest)request;
            MultipartFile multipartFile = mhsr.getFile("ProDefPic");
            InputStream in= multipartFile.getInputStream();
            processEngine.getRepositoryService()
                    .createDeployment().addInputStream(multipartFile.getOriginalFilename(),in).deploy();

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/deleteProDef")
    public Object deleteProDef(String id) {
        AjaxResult result = new AjaxResult();
        try {
            ProcessDefinition processDefinition  = processEngine.getRepositoryService()
                    .createProcessDefinitionQuery().processDefinitionId(id).singleResult();
            processEngine.getRepositoryService().deleteDeployment(processDefinition.getDeploymentId(),true);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
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
                map.put("id",processDefinition.getId());
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
