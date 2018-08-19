package com.github.sample;

import com.github.sample.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class KubeApplication {

    @Autowired
    private AppConfig appConfig;

    @GetMapping("")
    public ResponseEntity hell() {
        return ResponseEntity.ok(appConfig.getMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(KubeApplication.class, args);
    }

}
