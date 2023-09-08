package xyz.mwszksnmdys.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.mwszksnmdys.project.model.entity.InterfaceInfo;

/**
* @author mwszksnmdys
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-28 21:00:53
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
