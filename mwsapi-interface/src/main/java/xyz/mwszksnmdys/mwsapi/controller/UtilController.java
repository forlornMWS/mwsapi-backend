package xyz.mwszksnmdys.mwsapi.controller;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.mwszksnmdys.apiclientsdk.model.Md5Form;
import xyz.mwszksnmdys.apiclientsdk.utils.SignUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/util")
public class UtilController {

    @GetMapping("/uuid")
    public String GetUuid(HttpServletRequest request) {
        check(request);
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static void check(HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        if (!"mws".equalsIgnoreCase(accessKey)) {
            throw new RuntimeException("无权限");
        }
        // todo 随机数存redis
        if (Long.parseLong(nonce) < 10000) {
            throw new RuntimeException("无权限");
        }
        // todo  timestamp和当前时间不能超过指定区间
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        map.put("nonce", nonce);
        map.put("timestamp", timestamp);
        if (StringUtils.hasText(body)) {
            map.put("body", body);
        }
        String checkSign = SignUtil.generateSign(map, "abcdefgh");
        if (!checkSign.equalsIgnoreCase(sign)) {
            throw new RuntimeException("签名不正确");
        }
    }

    @PostMapping("/md5")
    public String md5Encrypt(@RequestParam String str, HttpServletRequest request) {
        check(request);
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    @PostMapping("/md5WithSalt")
    public String md5EncryptWithSalt(@RequestBody Md5Form form, HttpServletRequest request) {
        check(request);
        return DigestUtils.md5DigestAsHex((form.getSalt() + form.getStr()).getBytes());
    }


}
