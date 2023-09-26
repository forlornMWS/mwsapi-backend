package xyz.mwszksnmdys.common.service;

import xyz.mwszksnmdys.common.domain.InterfaceInfoDO;

public interface InnerInterfaceInfoService {
    /**
     * 根据path、method查询接口信息
     *
     * @param path   请求路径
     * @param method 请求方法
     * @return InterfaceInfo
     */
    InterfaceInfoDO getInvokeInterfaceInfo(String path, String method);
}
