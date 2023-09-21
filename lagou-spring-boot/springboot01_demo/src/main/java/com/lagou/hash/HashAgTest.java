package com.lagou.hash;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HashAgTest {
    // 普通hash算法
    public static void normalHash() {
        // 1. 客户端服务器地址
        List<String> ips = Arrays.asList("10.2.3.123", "10.2.4.126", "10.2.5.140");

        int serverCount = 4;

        ips.forEach(ip -> {
            int i = Math.abs(ip.hashCode()) % serverCount;
            System.out.println(ip + " 分配到服务器:[" + i + "]");
        });
    }

    // 一致性hash算法
    public static void consistentHashAlgorithmNoVirtual() {
        //  1. 初始化，将服务器节点IP的哈希值对应到哈希环上
        List<String> servers = Arrays.asList("123.111.0.0", "123.101.3.1", "111.20.35.2", "123.98.26.3");
        // 求出每个服务器的hash值，对应到hash环上，存储hash值与ip的对应关系
        Map<Integer, String> serversMap = servers.stream().collect(Collectors.toMap(s -> Math.abs(s.hashCode()), Function.identity()));
        SortedMap<Integer, String> sortedMap = new TreeMap<>(serversMap);
        // 2. 针对客户端ip求出hash值
        List<String> clients = Arrays.asList("10.78.12.3", "113.25.63.1", "126.12.3.8");
        clients.forEach(client -> {
            int i = Math.abs(client.hashCode());
            SortedMap<Integer, String> intergerSortedMap = sortedMap.tailMap(i);
            String server;
            if (intergerSortedMap.isEmpty()) {
                // 如果再排序的Mpa中没有找到就取第一个
                server = sortedMap.get(sortedMap.firstKey());
            } else {
                server = intergerSortedMap.get(intergerSortedMap.firstKey());
            }
            System.out.println(client + " 分配到服务器:[" + server + "]");

        });
    }

    public static void consistentHashAlgorithmWithVirtual() {
        //  1. 初始化，将服务器节点IP的哈希值对应到哈希环上
        List<String> servers = Arrays.asList("123.111.0.0", "123.101.3.1", "111.20.35.2", "123.98.26.3");
        // 求出每个服务器的hash值，对应到hash环上，存储hash值与ip的对应关系
        SortedMap<Integer, String> sortedMap = new TreeMap<>();
        int virtualNum = 3;
        servers.forEach(server -> {
            sortedMap.put(Math.abs(server.hashCode()), server);
            for (int i = 0; i < virtualNum; i++) {
                sortedMap.put(Math.abs((server + "#" + i).hashCode()), "虚拟节点" + server + "#" + i);
            }
        });

        // 2. 针对客户端ip求出hash值
        List<String> clients = Arrays.asList("10.78.12.3", "113.25.63.1", "126.12.3.8");
        clients.forEach(client -> {
            int i = Math.abs(client.hashCode());
            SortedMap<Integer, String> intergerSortedMap = sortedMap.tailMap(i);
            String server;
            if (intergerSortedMap.isEmpty()) {
                // 如果再排序的Mpa中没有找到就取第一个
                server = sortedMap.get(sortedMap.firstKey());
            } else {
                server = intergerSortedMap.get(intergerSortedMap.firstKey());
            }
            System.out.println(client + " 分配到服务器:[" + server + "]");

        });
    }

    public static void consistentHashAlgorithmNoVirtual2() {
//step1 初始化：把服务器节点IP的哈希值对应到哈希环上
// 定义服务器ip
        String[] tomcatServers = new String[]
                {"123.111.0.0", "123.101.3.1", "111.20.35.2", "123.98.26.3"};
        SortedMap<Integer, String> hashServerMap = new TreeMap<>();
        for (String tomcatServer : tomcatServers) {
// 求出每⼀个ip的hash值，对应到hash环上，存储hash值与ip的对应关系
            int serverHash = Math.abs(tomcatServer.hashCode());
// 存储hash值与ip的对应关系
            hashServerMap.put(serverHash, tomcatServer);
        }
//step2 针对客户端IP求出hash值
// 定义客户端IP
        String[] clients = new String[]
                {"10.78.12.3", "113.25.63.1", "126.12.3.8"};
        for (String client : clients) {
            int clientHash = Math.abs(client.hashCode());
//step3 针对客户端,找到能够处理当前客户端请求的服务器（哈希环上顺时针最近）
// 根据客户端ip的哈希值去找出哪⼀个服务器节点能够处理（）
            SortedMap<Integer, String> integerStringSortedMap =
                    hashServerMap.tailMap(clientHash);
            if (integerStringSortedMap.isEmpty()) {
// 取哈希环上的顺时针第⼀台服务器
                Integer firstKey = hashServerMap.firstKey();
                System.out.println("==========>>>>客户端： " + client + " 被路由到服务器： " + hashServerMap.get(firstKey));
            } else {
                Integer firstKey = integerStringSortedMap.firstKey();
                System.out.println("==========>>>>客户端： " + client + " 被路由到服务器： " + hashServerMap.get(firstKey));
            }
        }
    }

    public static void main(String[] args) {
//        normalHash();
        consistentHashAlgorithmNoVirtual();
        consistentHashAlgorithmNoVirtual2();
        consistentHashAlgorithmWithVirtual();
    }

}
