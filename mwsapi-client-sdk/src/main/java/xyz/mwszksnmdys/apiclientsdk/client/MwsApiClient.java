package xyz.mwszksnmdys.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import xyz.mwszksnmdys.apiclientsdk.utils.SignUtil;
import xyz.mwszksnmdys.common.exception.BusinessException;

import java.util.HashMap;

import static xyz.mwszksnmdys.common.common.ErrorCode.OPERATION_ERROR;

@Slf4j
public class MwsApiClient {

    private static final String REQUEST_BASE_URL = "http://localhost:8090";
    public MwsApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private final String accessKey;
    private final String secretKey;

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


    public String getUuid(String ignored) {
        HttpResponse response;
        try {
            response = HttpRequest.get(REQUEST_BASE_URL + "/api/util/uuid").addHeaders(getMap(""))
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("连接异常", e);
        }
        log.info(response.toString());
        if (!response.isOk()) {
            throw new BusinessException(OPERATION_ERROR.getCode(), OPERATION_ERROR.getMessage() + response.body());
        }
        return response.body();
    }

    public String md5Encrypt(String str) {
        String result = null;
        try {
            result = HttpRequest.post(REQUEST_BASE_URL + "/api/util/md5").addHeaders(getMap(str)).form(str).execute().body();
        } catch (Exception e) {
            throw new RuntimeException("连接异常", e);
        }
        log.info(result);
        return result;
    }

    public String md5EncryptWithSalt(String body) {
        String result = null;
        try {
            result = HttpRequest.post(REQUEST_BASE_URL + "/api/util/md5WithSalt").addHeaders(getMap(body)).body(body).execute().body();
        } catch (Exception e) {
            throw new RuntimeException("连接异常", e);
        }
        log.info(result);
        return result;
    }

}
