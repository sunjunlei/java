package com.example.service;

import com.example.entity.TaskEntity;
import org.quartz.SchedulerException;

import java.util.List;


/**
 * @author sjl
 */
public interface QuartzService {
    /**
     * 查询所有任务列表
     * @return
     */
    List<TaskEntity> findAll();

    /**
     * 添加一个任务
     * @param taskEntity
     */
    void addJob(TaskEntity taskEntity);

    /**
     * 根据Id查询一个对象
     * @param id
     * @return
     */
    TaskEntity findOneById(String id);

    /**
     * 更改任务的状态   开始  停止
     * @param jobId
     * @param cmd
     */
    void changeJobStatus(String jobId, String cmd) throws SchedulerException;

    /**
     * 更改任务 cron表达式 数据库
     * @param id
     * @param cron
     */
    void updateJobCron(String id,String cron) throws SchedulerException;

    /**
     * 增加一个任务
     * @param id
     */
    void addTask(String id) throws SchedulerException;

    /**
     * 暂停一个任务
     * @param id
     */
    void pauseJob(String id) throws SchedulerException;

    /**
     * 恢复暂停的任务
     * @param id
     */
    void resumeJob(String id) throws SchedulerException;

    /**
     * 立即开始执行
     * @param id
     */
    void runAJobNow(String id) throws SchedulerException;
}
