package com.lagou.timejob;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzMan {

    //创建调度器
    public static Scheduler createSchedule() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    public static JobDetail createJob() {
        JobBuilder jobBuilder = JobBuilder.newJob(TestJob.class);
        jobBuilder.withIdentity("jobName", "myJob");
        return jobBuilder.build();
    }

    public static Trigger createTrigger() {
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerName", "myTrigger")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();
        return trigger;
    }


    public static void main(String[] args) throws SchedulerException {
        Scheduler schedule = QuartzMan.createSchedule();
        JobDetail job = QuartzMan.createJob();
        Trigger trigger = QuartzMan.createTrigger();
        schedule.scheduleJob(job, trigger);
        schedule.start();
    }
}
