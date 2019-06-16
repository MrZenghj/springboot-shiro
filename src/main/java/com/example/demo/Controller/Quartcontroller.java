package com.example.demo.Controller;

import com.example.demo.entity.QuartzConfig;
import com.example.demo.quart.MySchedulerFactory;
import com.example.demo.service.AsyncQuartzConfService;
import com.example.demo.util.RestHttpReply;
import org.apache.ibatis.annotations.Param;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Quartcontroller {

    @Autowired
    private AsyncQuartzConfService asyncQuartzConfService;
    @Autowired
    private MySchedulerFactory mySchedulerFactory;
    //事务模版
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 查看所有任务
     * @return
     */
    @RequestMapping("/quartList")
    public RestHttpReply quartList(){
        RestHttpReply reply = new RestHttpReply();
        List<QuartzConfig> QuartzConfigList = asyncQuartzConfService.getJobList();
        reply.putData("quartzConfigList",QuartzConfigList);
        return reply;
    }

    /**
     * 更新任务(每次跟新任务前都需要暂停任务再重启任务)
     * @param id
     * @param cron 任務quart表达式
     * @param msg 任务信息
     * @return
     */
    @RequestMapping("/updateQuartzConfig")
    public RestHttpReply updateQuartzConfig(@Param("id") String id,@Param("cron")String cron,@Param("msg")String msg) throws SchedulerException, ClassNotFoundException {
        RestHttpReply reply = new RestHttpReply();
        //修改信息
        asyncQuartzConfService.updateJob(id,cron,msg);
        return reply;
    }

    /**
     *  恢复任务
     * @param id
     * @return
     */
    @RequestMapping("/resumeJob")
    public RestHttpReply resumeJob(@Param("id") String id)
            throws SchedulerException, ClassNotFoundException {
        return transactionTemplate.execute(new TransactionCallback<RestHttpReply>(){
            @Nullable
            @Override
            public RestHttpReply doInTransaction(TransactionStatus transactionStatus) {
                RestHttpReply reply = new RestHttpReply();
                try {
                    //修改状态
                    asyncQuartzConfService.updateJobStatus(id,true);
                    //恢复任务
                    mySchedulerFactory.resumeJob(id);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return reply;
            }
        });
    }

    /**
     *  暂停任务
     * @param id
     * @return
     */
    @RequestMapping("/pauseJob")
    public RestHttpReply pauseJob(@Param("id") String id){
        //暂停任务
        return transactionTemplate.execute(new TransactionCallback<RestHttpReply>(){
            @Nullable
            @Override
            public RestHttpReply doInTransaction(TransactionStatus transactionStatus) {
                RestHttpReply reply = new RestHttpReply();
                try {
                    //停止任务
                    mySchedulerFactory.pauseJob(id);
                    //修改状态
                    asyncQuartzConfService.updateJobStatus(id,false);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                return reply;
            }
        });
    }
}
