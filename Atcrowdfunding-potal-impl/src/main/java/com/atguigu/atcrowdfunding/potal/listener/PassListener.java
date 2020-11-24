package com.atguigu.atcrowdfunding.potal.listener;

import java.beans.ExceptionListener;

public class PassListener implements ExceptionListener {
    @Override
    public void exceptionThrown(Exception e) {
        System.out.println("PassListener.......");
    }
}
