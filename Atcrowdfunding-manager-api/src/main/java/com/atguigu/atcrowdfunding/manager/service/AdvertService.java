package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.util.Page;

import java.util.Map;

public interface AdvertService {
    Page queryAdvertList(Map<String, Object> map);

    int insert(Advertisement advert);
}
