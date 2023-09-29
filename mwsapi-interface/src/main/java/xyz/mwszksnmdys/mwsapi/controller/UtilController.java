package xyz.mwszksnmdys.mwsapi.controller;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import xyz.mwszksnmdys.apiclientsdk.model.Md5Form;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/util")
public class UtilController {

    @GetMapping("/uuid")
    public String GetUuid(HttpServletRequest request) {
        System.out.println(request.getHeader("mws"));
        return UUID.randomUUID().toString().replace("-", "");
    }


    @PostMapping("/md5")
    public String md5Encrypt(@RequestParam String str, HttpServletRequest request) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    @PostMapping("/md5WithSalt")
    public String md5EncryptWithSalt(@RequestBody Md5Form form, HttpServletRequest request) {
        System.out.println(form);
        return DigestUtils.md5DigestAsHex((form.getSalt() + form.getStr()).getBytes());
    }


}
