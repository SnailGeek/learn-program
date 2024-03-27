package client;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.requests.DescribeLogDirsResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MyAdminClient {
    private KafkaAdminClient client;

    @Before
    public void before() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, "adminclient-1");
        client = (KafkaAdminClient) KafkaAdminClient.create(configs);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void testListTopic1() throws ExecutionException, InterruptedException {
        ListTopicsResult listTopicsResult = client.listTopics();

        System.out.println("======主题列表========");
        Collection<TopicListing> topicListings = listTopicsResult.listings().get();
        topicListings.forEach(new Consumer<TopicListing>() {
            @Override
            public void accept(TopicListing topicListing) {
                boolean internal = topicListing.isInternal();
                String name = topicListing.name();
                String str = topicListing.toString();
                System.out.println(str + "\t" + name + "\t" + internal);
            }
        });

        System.out.println("======主题名称========");
        Set<String> strings = listTopicsResult.names().get();
        System.out.println(strings);

        System.out.println("===主题名称和内容映射===");
        Map<String, TopicListing> stringTopicListingMap = listTopicsResult.namesToListings().get();
        stringTopicListingMap.forEach((k, v) -> {
            System.out.println(k + "\t" + v);
        });

        System.out.println("===带筛选条件===");
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);
        options.timeoutMs(500);
        ListTopicsResult listTopicsResult1 = client.listTopics(options);
        Map<String, TopicListing> stringTopicListingMap1 = listTopicsResult1.namesToListings().get();
        stringTopicListingMap1.forEach((k, v) -> {
            System.out.println(k + "\t" + v);
        });
    }

    @Test
    public void testCreatTopic() throws ExecutionException, InterruptedException {
        Map<String, String> configs = new HashMap<>();
        configs.put("max.message.bytes", "1048576");
        configs.put("segment.bytes", "1048576000");
        NewTopic newTopic = new NewTopic("tp_demo_02", 3, (short) 1);
        newTopic.configs(configs);

        CreateTopicsResult topics = client.createTopics(List.of(newTopic));
        KafkaFuture<Void> all = topics.all();
        Void avoid = all.get();
        System.out.println(avoid);
    }

    @Test
    public void testDeleteTopic() throws ExecutionException, InterruptedException {
        DeleteTopicsOptions deleteTopicsOptions = new DeleteTopicsOptions();
        deleteTopicsOptions.timeoutMs(500);
        DeleteTopicsResult result = client.deleteTopics(List.of("tp_demo_02"), deleteTopicsOptions);
        result.all().get();
    }

    @Test
    public void testAlterTopic() throws ExecutionException, InterruptedException {
        NewPartitions newPartitions = NewPartitions.increaseTo(5);
        Map<String, NewPartitions> newPartitionsMap = new HashMap<>();
        newPartitionsMap.put("adm_tp_01", newPartitions);
        CreatePartitionsOptions option = new CreatePartitionsOptions();

        // 如果只是验证，不是创建分区则设置为true
//        option.validateOnly(true);

        CreatePartitionsResult result = client.createPartitions(newPartitionsMap, option);
        Void unused = result.all().get();
    }

    @Test
    public void testDescribeTopic() throws ExecutionException, InterruptedException {
        DescribeTopicsOptions options = new DescribeTopicsOptions();
        options.timeoutMs(500);

        DescribeTopicsResult result = client.describeTopics(List.of("adm_tp_01"), options);
        Map<String, TopicDescription> stringTopicDescriptionMap = result.all().get();
        stringTopicDescriptionMap.forEach((k, v) -> {
            System.out.println(k + "\t" + v);
            System.out.println("=======================================");
            System.out.println(k);
            boolean internal = v.isInternal();
            String name = v.name();
            List<TopicPartitionInfo> partitions = v.partitions();
            String partionstr = Arrays.toString(partitions.toArray());
            System.out.println("内部的：" + internal);
            System.out.println("topic name=" + name);
            System.out.println("分区：" + partionstr);
            partitions.forEach(System.out::println);
        });
    }

    @Test
    public void testDescribeCluster() throws ExecutionException, InterruptedException {
        DescribeClusterResult describeClusterResult = client.describeCluster();
        String clusterName = describeClusterResult.clusterId().get();
        System.out.println("cluster name : " + clusterName);

        Node node = describeClusterResult.controller().get();
        System.out.println("集群控制器：" + node);

        Collection<Node> nodes = describeClusterResult.nodes().get();
        nodes.forEach(System.out::println);
    }

    @Test
    public void testDescribeConfigs() throws ExecutionException, InterruptedException, TimeoutException {
        ConfigResource configResource = new ConfigResource(ConfigResource.Type.BROKER, "0");
        DescribeConfigsResult describeConfigsResult = client.describeConfigs(List.of(configResource));
        Map<ConfigResource, Config> configMap = describeConfigsResult.all().get(15, TimeUnit.SECONDS);
        configMap.forEach(new BiConsumer<ConfigResource, Config>() {
            @Override
            public void accept(ConfigResource configResource, Config config) {
                ConfigResource.Type type = configResource.type();
                String name = configResource.name();
                System.out.println("资源名称：" + name);

                Collection<ConfigEntry> entries = config.entries();
                entries.forEach(new Consumer<ConfigEntry>() {
                    @Override
                    public void accept(ConfigEntry configEntry) {
                        boolean aDefault = configEntry.isDefault();
                        boolean readOnly = configEntry.isReadOnly();
                        boolean sensitive = configEntry.isSensitive();
                        String name1 = configEntry.name();
                        String value = configEntry.value();
                        System.out.println("是否默认：" + aDefault + "\t是否只读？" + readOnly + "\t是否敏感？" + sensitive + "\t配置项名称：" + name1 + "\t配置项值：" + value);
                    }
                });
                ConfigEntry retries = config.get("retries");
                if (retries != null) {
                    System.out.println(retries.name() + " --->" + retries.value());
                } else {
                    System.out.println("没有这个属性");
                }
            }
        });
    }

    @Test
    public void testAlterConfig() throws ExecutionException, InterruptedException {
        Map<ConfigResource, Config> configMap = new HashMap<>();
        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, "adm_tp_01");
        Config config = new Config(Collections.singleton(new ConfigEntry("segment.bytes", "1048576000")));
        configMap.put(configResource, config);
        AlterConfigsResult alterConfigsResult = client.alterConfigs(configMap);
        alterConfigsResult.all().get();
    }

    @Test
    public void testDescribeLogDirs() throws ExecutionException, InterruptedException {
        DescribeLogDirsOptions options = new DescribeLogDirsOptions();
        options.timeoutMs(1_000);
        DescribeLogDirsResult describeLogDirsResult = client.describeLogDirs(Collections.singleton(0), options);
        Map<Integer, Map<String, DescribeLogDirsResponse.LogDirInfo>> integerMapMap =
                describeLogDirsResult.all().get();
        integerMapMap.forEach(new BiConsumer<Integer, Map<String, DescribeLogDirsResponse.LogDirInfo>>() {
            @Override
            public void accept(Integer integer, Map<String, DescribeLogDirsResponse.LogDirInfo> stringLogDirInfoMap) {
                System.out.println("broker.id = " + integer);
                stringLogDirInfoMap.forEach(new BiConsumer<String, DescribeLogDirsResponse.LogDirInfo>() {
                    @Override
                    public void accept(String s, DescribeLogDirsResponse.LogDirInfo logDirInfo) {
                        System.out.println("log.dirs = " + s);
                        logDirInfo.replicaInfos.forEach(new BiConsumer<TopicPartition, DescribeLogDirsResponse.ReplicaInfo>() {
                            @Override
                            public void accept(TopicPartition topicPartition, DescribeLogDirsResponse.ReplicaInfo replicaInfo) {
                                int partition = topicPartition.partition();
                                String topic = topicPartition.topic();
                                boolean isFuture = replicaInfo.isFuture;
                                long offsetLag = replicaInfo.offsetLag;
                                long size = replicaInfo.size;
                                System.out.println("partition: " + partition + " topic: " + topic + " isFuture: "
                                        + isFuture + " offsetLag: " + offsetLag + " size: " + size);
                            }
                        });
                    }
                });
            }
        });
    }
}
