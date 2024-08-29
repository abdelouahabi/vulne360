package org.vulnepro.nvdproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.vulnepro.nvdproject")
public class NvdprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NvdprojectApplication.class, args);
    }

}

