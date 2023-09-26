package xyz.mwszksnmdys.common.service;

import xyz.mwszksnmdys.common.domain.UserDO;

public interface InnerUserService {
    /**
     * 根据accessKey查询用户
     *
     * @param accessKey accessKey
     * @return User
     */
    UserDO getInvokeUser(String accessKey);
}
