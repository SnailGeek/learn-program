package job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class ElasticJobMain {
    public static void main(String[] args) {
//        extractedJob();
        extractedJobSharding();
    }

    private static void extractedJob() {
        // 配置分布式协调服务（注册中心）Zookeeper
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration("192.168.233.128:2181",
                "data-archive-job");
        CoordinatorRegistryCenter center = new ZookeeperRegistryCenter(zkConfig);
        center.init();

        // 配置任务（时间事件、定时任务业务逻辑、调度器）
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                .newBuilder("archive-job", "*/3 * * * * ?", 1)
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(jobCoreConfiguration,
                ArchiveJob.class.getName());

        JobScheduler jobScheduler = new JobScheduler(center,
                LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build());
        jobScheduler.init();
    }

    private static void extractedJobSharding() {
        // 配置分布式协调服务（注册中心）Zookeeper
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration("192.168.233.128:2181",
                "data-archive-job-sharding");
        CoordinatorRegistryCenter center = new ZookeeperRegistryCenter(zkConfig);
        center.init();

        // 配置任务（时间事件、定时任务业务逻辑、调度器）
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                .newBuilder("archive-job", "*/3 * * * * ?", 3)
                .shardingItemParameters("0=bachelor,1=master,2=doctor")
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(jobCoreConfiguration,
                ArchiveJobSharding.class.getName());

        JobScheduler jobScheduler = new JobScheduler(center,
                LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build());
        jobScheduler.init();
    }
}
