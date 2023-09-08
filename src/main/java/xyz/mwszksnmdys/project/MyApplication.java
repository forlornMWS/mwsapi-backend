package xyz.mwszksnmdys.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import xyz.mwszksnmdys.project.constant.EnvConstant;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MyApplication.class, args);
        ConfigurableEnvironment env = context.getEnvironment();
        String ip = null;
        try {
            ip = Optional.ofNullable(InetAddress.getLocalHost().getHostAddress()).orElse("");
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        String port = env.getProperty("server.port");
        String path = Optional.ofNullable(env.getProperty("server.servlet.context-path")).orElse("");
        log.info("本地文档地址: http://{}:{}{}/doc.html", EnvConstant.LOCAL_HOST, port, path);
        log.info("IP文档地址: http://{}:{}{}/doc.html", ip, port, path);
    }

}
