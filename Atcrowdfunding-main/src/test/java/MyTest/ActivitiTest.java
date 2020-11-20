package MyTest;

import com.atguigu.atcrowdfunding.activiti.listener.NoListener;
import com.atguigu.atcrowdfunding.activiti.listener.YesListener;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitiTest {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

    //监听器的使用
    @Test
    public void test13(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance="+processInstance);
        TaskService taskService =processEngine.getTaskService();
        TaskQuery query = taskService.createTaskQuery();
        List<Task> tasks = query.taskAssignee("zhangsan").list();
        for(Task task:tasks){
            taskService.setVariable(task.getId(),"flag",true);
            taskService.setVariable(task.getId(),"yesListener",new YesListener());
            taskService.setVariable(task.getId(),"noListener",new NoListener());
            taskService.complete(task.getId());
        }
    }

    @Test
    public void test12(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> tasks = taskQuery.taskAssignee("lisi").list();
        for (Task task:tasks){
            taskService.complete(task.getId());
        }
    }

    //并行网关
    @Test
    public void test11(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance="+processInstance);
    }

    //执行排他网关任务
    @Test
    public  void test10(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
/*        List<Task> tasks =  taskQuery.taskAssignee("zhangsan").list();
        for(Task task:tasks){
            System.out.println("任务："+task.getName());
            taskService.complete(task.getId());
        }*/
        List<Task> tasks =taskQuery.taskDefinitionKey("_9").list();
        for(Task task:tasks){
            System.out.println("任务："+task.getName());
            taskService.complete(task.getId());
        }
    }

    //启动排他网关流程任务
    @Test
    public void test09(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map = new HashMap<>();
        map.put("day",4);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),map);
        System.out.println("processInstance="+processInstance);
    }


    //定义变量
    @Test
    public void test08(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map = new HashMap<>();
        map.put("tl","zhangsan");
        map.put("pm","lisi");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),map);
        System.out.println("processInstance="+processInstance);
    }


    //任务领取
    @Test
    public void test07(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.taskCandidateGroup("team").list();
        //领取前zhangsna任务数量
        long count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("领取前zhangsan任务数量："+count);

        for(Task task:taskList){
            taskService.claim(task.getId(),"zhangsan");
        }

        //领取后zhangsna任务数量
        TaskQuery taskQuery2 = taskService.createTaskQuery();
        long count2 = taskQuery2.taskAssignee("zhangsan").count();
        System.out.println("领取后zhangsan任务数量："+count2);
    }

    //查询历史流程
    @Test
    public void test06(){
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.processInstanceId("201").finished().singleResult();
        System.out.println(historicProcessInstance);
    }

    //执行流程任务
    @Test
    public void test05(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        //获取zhangsan 的任务列表
        List<Task> list = taskQuery.taskAssignee("zhangsan").list();
        List<Task> list2 = taskQuery.taskAssignee("lisi").list();

        for(Task task:list){
            System.out.println("zhangsan任务："+task.getName());
        }
        System.out.println("-----------------------------");
        for(Task task:list2){
            System.out.println("lisi任务："+task.getName());
            taskService.complete(task.getId());
        }
    }

    //启动流程实例
    /*
    * act_hi_actinst,历史的活动的任务表
    * act_hi_procinst,历史的流程实例表
    * act_hi_taskinst,历史的流程的任务表
    * act_ru_execution,正在运行的任务表
    * act_ru_task,运行的任务数据表
    * */
    @Test
    public void test04(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance="+processInstance);
    }

    //查询部署流程定义
    @Test
    public void test03(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        //查询全部
        List<ProcessDefinition> list= query.list();
        for(ProcessDefinition processDefinition:list){
            System.out.println("Key="+processDefinition.getKey());
            System.out.println("Id="+processDefinition.getId());
            System.out.println("Version="+processDefinition.getVersion());
        }
        System.out.println("+++++++++++++++++++++++++++++");
        //查询记录数
        long count= query.count();
        System.out.println(count);

        System.out.println("~~~~~~~~~~~~~~~~~~");
        //查询最后一次部署的流程定义
        ProcessDefinition processDefinition2 = query.latestVersion().singleResult();
        System.out.println("Key2="+processDefinition2.getKey());
        System.out.println("Id2="+processDefinition2.getId());
        System.out.println("Version2="+processDefinition2.getVersion());

    }

    //部署流程定义
    @Test
    public void test02(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("MyProcess8.bpmn").deploy();

    }

    //流程引擎对象
    @Test
    public void test01(){
        System.out.println(processEngine);
    }
}
