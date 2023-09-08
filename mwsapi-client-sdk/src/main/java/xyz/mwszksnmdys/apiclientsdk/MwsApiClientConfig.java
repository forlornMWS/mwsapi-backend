package xyz.mwszksnmdys.apiclientsdk;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import xyz.mwszksnmdys.apiclientsdk.client.MwsApiClient;

@Configuration
@ConfigurationProperties("mws.api")
@Data
@ComponentScan
public class MwsApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public MwsApiClient mwsApiClient() {
        MwsApiClient mwsApiClient = new MwsApiClient(accessKey, secretKey);
        return mwsApiClient;
    }
}
