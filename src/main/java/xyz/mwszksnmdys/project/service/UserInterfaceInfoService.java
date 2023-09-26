package xyz.mwszksnmdys.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.mwszksnmdys.project.model.entity.UserInterfaceInfo;

/**
* @author mwszksnmdys
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用次数统计
     */
    boolean invokeInterfaceCount(long interfaceInfoId, long userId);
}
