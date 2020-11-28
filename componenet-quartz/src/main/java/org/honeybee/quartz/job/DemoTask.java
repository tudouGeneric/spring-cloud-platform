package org.honeybee.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 第一种集成Quartz, 定义任务后需要去Configuration里配置相关JobDetail和Trigger
 */
@Component
@Slf4j
public class DemoTask extends QuartzJobBean {

    public static final String TASK_NAME = "demoTask";
    public static final String TASK_GROUP = "demoGroup";

    @Override
    protected void executeInternal(JobExecutionContext job) throws JobExecutionException {
        log.info("==========DemoTask执行:" + System.currentTimeMillis());
        //获取参数
        JobDataMap jobDataMap = job.getJobDetail().getJobDataMap();
        //执行业务逻辑...
    }

}
