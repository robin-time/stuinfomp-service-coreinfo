package com.lxy.stuinfomp.service.coreinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.lxy.stuinfomp")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.lxy.stuinfomp.commons.mapper")
@EnableSwagger2
public class StuinfompServiceCoreinfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(StuinfompServiceCoreinfoApplication.class,args);
    }
}
