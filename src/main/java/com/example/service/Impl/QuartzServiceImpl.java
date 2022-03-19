package com.example.service.Impl;

import com.example.conf.TaskObject;
import com.example.entity.TaskEntity;
import com.example.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author sjl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private Scheduler scheduler;
    private final String STOP = "STOP";
    private final String START = "START";

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<TaskEntity> findAll() {
        return null;
    }


    /**
     * 数据库添加一个任务
     * @param taskEntity
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public void addJob(TaskEntity taskEntity) {
        taskEntity.setJobId(UUID.randomUUID().toString());
        taskEntity.preCreate();
        taskEntity.setJobStatus(TaskEntity.STATUS_NOT_RUNNING);
        //taskDao.save(taskEntity);

    }

    /**
     * 数据库根据Id查询一个任务详情
     * @param id
     * @return
     */
    @Override
    public TaskEntity findOneById(String id) {
        return null;//taskDao.findById(id).get();
    }

    /**
     * 停止开始运行任务
     * @param jobId
     * @param cmd 操作类型  stop停止  start开始
     */
    @Override
    public void changeJobStatus(String jobId, String cmd) throws SchedulerException {
        TaskEntity oneById = findOneById(jobId);
        if(oneById == null){
            return;
        }
        //停止
        if(STOP.equals(cmd)){
            //定位任务
            JobKey jobKey = JobKey.jobKey(oneById.getJobName(), oneById.getJobGroup());
            //停止任务
            oneById.setJobStatus(TaskEntity.STATUS_NOT_RUNNING);
            scheduler.deleteJob(jobKey);
        }else if(START.equals(cmd)){
            //开始一个任务
            oneById.setJobStatus(TaskEntity.STATUS_RUNNING);
            //重新开始  添加一个任务
            addJob(oneById);

        }else{
            return;
        }
        //将修改信息保存再数据库
        //taskDao.save(oneById);
    }

    /**
     * 修改任务运行信息 cron
     * @param id
     * @param cron
     */
    @Override
    public void updateJobCron(String id, String cron) throws SchedulerException {
        TaskEntity job = findOneById(id);
        if(job == null){
            return;
        }
        //开始修改运行周期
        job.setCronExpression(cron);

        //判断任务是否再运行中 如果是运行中就执行job修改 如果没有运行则只执行修改数据库即可
        if(TaskEntity.STATUS_RUNNING.equals(job.getJobStatus())){
            //精确任务
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取这个任务的触发器
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            //设置新的定时器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            //给触发器绑定新的定时任务
            trigger = trigger.getTriggerBuilder()
                    .withSchedule(cronScheduleBuilder)
                    .withIdentity(triggerKey)
                    .build();
            //绑定定时器和触发器
            scheduler.rescheduleJob(triggerKey, trigger);

        }
    }

    @Override
    public void addTask(String id) throws SchedulerException {
        TaskEntity job = findOneById(id);
        if(job == null || TaskEntity.STATUS_RUNNING.equals(job.getJobStatus())){
            return;
        }
        //创建任务信息
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        //创建一个触发器
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //查看此触发器是否存在  有可能存在  因为启动任务也调用了这个方法
        if(null == cronTrigger){
            //如果不存在  开始创建一个定时任务
            JobDetail jobDetail = JobBuilder.newJob(TaskObject.class)
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .build();
            //将任务对对象产地给任务工厂 生产任务
            jobDetail.getJobDataMap().put("job",job);
            //创建一个定时器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            //创建新的触发器
            cronTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(scheduleBuilder)
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .build();
            //绑定触发器和任务详情
            scheduler.scheduleJob(jobDetail,cronTrigger);
        }else{
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            //修改触发器
            cronTrigger = cronTrigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            //按照新的运行
            scheduler.rescheduleJob(triggerKey,cronTrigger);
        }
        job.setJobStatus(TaskEntity.STATUS_RUNNING);
        //taskDao.save(job);
    }

    /**
     * 暂停一个任务
     * @param id
     */
    @Override
    public void pauseJob(String id) throws SchedulerException {
        TaskEntity job = findOneById(id);
        //获取任务详细  key
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        //暂停
        scheduler.pauseJob(jobKey);
    }

    /**
     * 开始任务  恢复暂停
     */
    @Override
    public void resumeJob(String id) throws SchedulerException {
        TaskEntity job = findOneById(id);
        //获取任务详细  key
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 不等待  立即执行该任务
     * @param id
     */
    @Override
    public void runAJobNow(String id) throws SchedulerException {
        TaskEntity job = findOneById(id);
        //获取任务详细  key
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.triggerJob(jobKey);
    }
}
