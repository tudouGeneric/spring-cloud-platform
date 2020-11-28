package org.honeybee.quartz.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.honeybee.quartz.job.DemoTask;
import org.quartz.*;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

/**
 * Quartz配置类
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {

    /**
     * 配置Quartz独立数据源的配置
     */
    /*@Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource quartzDataSource(){
        return new DruidDataSource();
    }*/

    /**
     * 定义DemoJobDetail
     * @return
     */
    /*@Bean
    public JobDetail demoTaskJobDetail() {
        return JobBuilder.newJob(DemoTask.class).withIdentity(DemoTask.TASK_NAME, DemoTask.TASK_GROUP)
                //是否持久化
                .storeDurably().build();
    }

    @Bean
    public Trigger demoTaskTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(demoTaskJobDetail())
                .withIdentity(DemoTask.TASK_NAME, DemoTask.TASK_GROUP)
                .withSchedule(cronScheduleBuilder)
                .build();
    }*/

}
