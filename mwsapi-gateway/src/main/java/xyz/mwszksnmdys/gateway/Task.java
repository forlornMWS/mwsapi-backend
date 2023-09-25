package xyz.mwszksnmdys.gateway;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.mwszksnmdys.project.rpc.RpcDemoService;

import java.util.Date;

@Component
public class Task implements CommandLineRunner {

    @DubboReference
    private RpcDemoService rpcDemoService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(test());


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(new Date() + test());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private String test() {
        String result = rpcDemoService.sayHello(" World~~~");
        return "Received result ---> " + result;
    }
}
