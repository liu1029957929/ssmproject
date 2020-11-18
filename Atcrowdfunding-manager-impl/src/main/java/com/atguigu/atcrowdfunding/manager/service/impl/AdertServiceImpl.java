package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdertServiceImpl implements AdvertService {
    @Resource
    private AdvertisementMapper advertisementMapper;

    @Override
    public Page queryAdvertList(Map<String, Object> map) {
        Integer pageno = (Integer) map.get("pageno");
        Integer pagesize = (Integer) map.get("pagesize");
        Page page = new Page(pageno,pagesize);

        //获取的roleList的起始坐标
        Integer startIndex = page.getStartIndex(pageno,pagesize);
        //获取roleList
        List<Advertisement> advertList = advertisementMapper.queryAdvertList(startIndex,pagesize);
        //获取总的角色数目
        Integer totalsize = advertisementMapper.queryAllAdvert();
        //封装到page中
        page.setAdvertList(advertList);
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public int insert(Advertisement advert) {
        return advertisementMapper.insert(advert);
    }
}
