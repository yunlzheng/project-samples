package com.github.sample;

import com.github.sample.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class KubeApplication {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("")
    public ResponseEntity hell() {
        return ResponseEntity.ok(appConfig.getMessage());
    }

    @GetMapping("/services")
    public ResponseEntity services() {
        return ResponseEntity.ok(discoveryClient.getServices());
    }

    @GetMapping("/services/{name}")
    public ResponseEntity service(@PathVariable("name") String name) {
        List<ServiceInstance> instances = discoveryClient.getInstances(name);
        return ResponseEntity.ok(instances);
    }

    public static void main(String[] args) {
        SpringApplication.run(KubeApplication.class, args);
    }

}
