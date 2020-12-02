package org.honeybee.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * 第二种集成Quartz,定义可动态修改的任务
 *  没有显示注册到Quartz中进行管理
 */
//@Configuration
//@Component
@Slf4j
public class DemoDynamicTask implements SchedulingConfigurer {

    /**
     * cron表达式(以下参数都可以设置成从配置文件读取)
     */
    private  String cron="0/30 * * * * ?";
    /**
     * 任务名称
     */
    private String name="";
    /**
     * 自定义参数
     */
    private String jobData="";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(doTask(jobData), getTrigger());
    }

    /**
     * 业务执行
     * @param jobData
     * @return
     */
    private Runnable doTask(String jobData) {
        Runnable runnable = () -> {
            log.info("=========DempDynamicTask执行,jobData=" + jobData);
            //执行业务逻辑...
        };

        return runnable;
    }

    /**
     * 业务触发器
     * @return
     */
    private Trigger getTrigger() {
        Trigger trigger = triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger(cron);
            return cronTrigger.nextExecutionTime(triggerContext);
        };
        return trigger;
    }


    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

}
