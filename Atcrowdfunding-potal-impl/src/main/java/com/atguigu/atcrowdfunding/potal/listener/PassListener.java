package com.atguigu.atcrowdfunding.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.beans.ExceptionListener;

public class PassListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("PassListener.......");
    }
}
