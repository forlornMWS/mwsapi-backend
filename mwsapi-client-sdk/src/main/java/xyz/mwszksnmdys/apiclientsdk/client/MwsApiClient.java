package xyz.mwszksnmdys.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import xyz.mwszksnmdys.apiclientsdk.model.Md5Form;
import xyz.mwszksnmdys.apiclientsdk.utils.SignUtil;

import java.util.HashMap;

@Slf4j
public class MwsApiClient {
    public MwsApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private String accessKey;
    private String secretKey;
    private String nonce;
    private String sign;
    private String timestamp;

    private HashMap<String, String> getMap(String body) {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
//        map.put("secretKey", secretKey);
        map.put("nonce", RandomUtil.randomNumbers(10));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        if (StringUtils.hasText(body)) {
            map.put("body", body);
        }
        map.put("sign", SignUtil.generateSign(map, secretKey));
        return map;
    }


    public String getUuid() {
        String result = null;
        try {
            result = HttpRequest.get("http://localhost:8123/api/util/uuid").addHeaders(getMap(""))
                    .execute().body();
        } catch (Exception e) {
            throw new RuntimeException("连接异常", e);
        }
        log.info(result);
        return result;
    }

    public String md5Encrypt(String str) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("str", "hello");
        String result = null;
        try {
            result = HttpRequest.post("http://localhost:8123/api/util/md5").addHeaders(getMap(str)).form(params).execute().body();
        } catch (Exception e) {
            throw new RuntimeException("连接异常", e);
        }
        log.info(result);
        return result;
    }

    public String md5EncryptWithSalt(Md5Form form) {
        String str = JSONUtil.toJsonStr(form);
        String result = null;
        try {
            result = HttpRequest.post("http://localhost:8123/api/util/md5WithSalt").addHeaders(getMap(str)).body(str).execute().body();
        } catch (Exception e) {
            throw new RuntimeException("连接异常", e);
        }
        log.info(result);
        return result;
    }

}
