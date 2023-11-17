package com.snail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/autodeliver")
public class AutoDeliverController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/checkState/{userId}")
    public Integer checkState(@PathVariable Long userId) {
        return restTemplate.getForObject("http://localhost:8081/resume/openstate/" + userId, Integer.class);

    }


    @GetMapping("/checkState/discovery/{userId}")
    public Integer checkStateByDiscovery(@PathVariable Long userId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("learn-cloud-resume");
//        ServiceInstance serviceInstance = instances.get(0);
        ServiceInstance serviceInstance = instances.stream().filter(instance -> "gray".equals(instance.getMetadata().get("tag"))).findFirst().get();
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = String.format("http://%s:%s/resume/openstate/%s", host, port, userId);
        return restTemplate.getForObject(url, Integer.class);
    }
}
