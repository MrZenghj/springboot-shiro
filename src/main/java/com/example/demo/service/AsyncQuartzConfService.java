package com.example.demo.service;


import com.example.demo.entity.QuartzConfig;
import com.example.demo.mapper.QuartzConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * Created by jinyu on 2018/4/13/013.
 */
@Service
@Transactional
public class AsyncQuartzConfService {

    //private TransactionTemplate transactionTemplate;

    @Autowired
    QuartzConfigMapper quartzConfigMapper;

    public List<QuartzConfig> getJobList() {
        List<QuartzConfig> quartzConfigList = quartzConfigMapper.selectAll();
        return quartzConfigList;
    }

    public QuartzConfig findById(String id) {
        return quartzConfigMapper.selectByPrimaryKey(id);
    }

    public int updateJob(String id, String cron, String msg) {
        QuartzConfig quartzConfig = quartzConfigMapper.selectByPrimaryKey(id);
        quartzConfig.setCron(cron);
        quartzConfig.setMsg(msg);
        return quartzConfigMapper.updateByPrimaryKey(quartzConfig);
    }

    public int updateJobStatus(String id, boolean status) {
        QuartzConfig quartzConfig = quartzConfigMapper.selectByPrimaryKey(id);
        quartzConfig.setStatus(status);
        return quartzConfigMapper.updateByPrimaryKey(quartzConfig);
    }
}
