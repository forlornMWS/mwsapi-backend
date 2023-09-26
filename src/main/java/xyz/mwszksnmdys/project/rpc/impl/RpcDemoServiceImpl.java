package xyz.mwszksnmdys.project.rpc.impl;

import org.apache.dubbo.config.annotation.DubboService;
import xyz.mwszksnmdys.common.service.RpcDemoService;

@DubboService
public class RpcDemoServiceImpl implements RpcDemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
