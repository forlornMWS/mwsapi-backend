package xyz.mwszksnmdys.project.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.mwszksnmdys.project.service.UserInterfaceInfoService;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceImplTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeCount() {
        boolean b = userInterfaceInfoService.invokeInterfaceCount(1L, 1L);
        Assertions.assertTrue(b);
    }
}