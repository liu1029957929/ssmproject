package MyTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ActivitiTest {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

    //创建流程实例
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
        System.out.println(processInstance);
    }

    //查询部署流程定义
    @Test
    public void test03(){
        System.out.println("--------------------");
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
        repositoryService.createDeployment().addClasspathResource("MyProcess.bpmn").deploy();
    }

    @Test
    public void test01(){
        System.out.println(processEngine);
    }
}
