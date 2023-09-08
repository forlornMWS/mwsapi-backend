package xyz.mwszksnmdys.apiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class SignUtil {

    public static String generateSign(HashMap<String, String> map, String secretKey) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String encrypt = digester.digestHex((map.toString() + "." + secretKey).getBytes());
        log.info("生成的签名: {}", encrypt);
        return encrypt;
    }
}
