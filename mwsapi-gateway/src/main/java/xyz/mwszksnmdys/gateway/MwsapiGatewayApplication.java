package xyz.mwszksnmdys.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = {"xyz.mwszksnmdys"})
public class MwsapiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MwsapiGatewayApplication.class, args);
    }

}
