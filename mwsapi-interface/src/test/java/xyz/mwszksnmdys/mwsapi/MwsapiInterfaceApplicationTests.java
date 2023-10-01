package xyz.mwszksnmdys.mwsapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.mwszksnmdys.apiclientsdk.client.MwsApiClient;
import xyz.mwszksnmdys.apiclientsdk.model.Md5Form;

import javax.annotation.Resource;

@SpringBootTest
class MwsapiInterfaceApplicationTests {

    @Resource
    private MwsApiClient mwsApiClient;

    @Test
    void contextLoads() {
//        System.out.println(mwsApiClient.getUuid());
//        System.out.println(mwsApiClient.md5Encrypt("hello"));
    }

}
