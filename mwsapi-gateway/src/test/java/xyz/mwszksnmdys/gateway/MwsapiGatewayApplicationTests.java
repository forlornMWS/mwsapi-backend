package xyz.mwszksnmdys.gateway;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.mwszksnmdys.project.rpc.RpcDemoService;

@SpringBootTest
class MwsapiGatewayApplicationTests {

    @DubboReference
    private RpcDemoService rpcDemoService;

    @Test
    void contextLoads() {
        String s = rpcDemoService.sayHello("test Name");
        System.out.println(s);
    }

}
