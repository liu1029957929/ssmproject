package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
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
@RequestMapping("authcert")
public class AuthcertController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("index")
    public String index(){
        return "authcert/index";
    }


    @ResponseBody
    @RequestMapping("/showpage")
    public Object showpage(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize) {
        AjaxResult result = new AjaxResult();
        try {
            Page page  = new Page(pageno,pagesize);
            List<Map<String,Object>> mlist = new ArrayList<>();

            Integer startIndex = page.getStartIndex(pageno,pagesize);
            //查询流程任务（根据auth和组名来查询）
            List<Task> taskList =  taskService.createTaskQuery().processDefinitionKey("auth")
                    .taskCandidateGroup("backuser").listPage(startIndex,pagesize);
            for(Task task:taskList){
                Map<String,Object> map = new HashMap<>();
                //查询流程定义（根据流程定义id来查询）
                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionId(task.getProcessDefinitionId()).singleResult();
                map.put("procDefName",pd.getName());
                map.put("procDefVersion",pd.getVersion());
                map.put("Name",task.getName());

                //查询member（根据流程任务中的piid）
                Member member =memberService.queryMemberById(task.getProcessInstanceId());
                map.put("memberName",member.getLoginacct());

                mlist.add(map);
            }
            page.setData(mlist);
            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }
}
