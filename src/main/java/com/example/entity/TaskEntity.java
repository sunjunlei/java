package com.example.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务实体映射
 * @author sjl
 */
@Data
public class TaskEntity implements Serializable {
    /**
     * 运行中
     */
    public static final String STATUS_RUNNING = "0";
    /**
     * 停止
     */
    public static final String STATUS_NOT_RUNNING = "1";
    private String jobId;
    private Date createTime;
    private Date updateTime;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务启动状态
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 具体类名  全限定名
     */
    private String runJobClassName;
    /**
     * 任务状态
     */
    private String isConcurrent;
    /**
     * 任务方法名
     */
    private String runJobMethodName;

    public void preCreate(){
        this.createTime = new Date();
    }
    public void preUpdate(){
        this.updateTime = new Date();
    }

    public TaskEntity() {
    }

    public TaskEntity(Date createTime, Date updateTime, String jobName, String jobGroup, String jobStatus, String cronExpression, String description, String runJobClassName, String isConcurrent, String runJobMethodName) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobStatus = jobStatus;
        this.cronExpression = cronExpression;
        this.description = description;
        this.runJobClassName = runJobClassName;
        this.isConcurrent = isConcurrent;
        this.runJobMethodName = runJobMethodName;
    }
}
