package xyz.mwszksnmdys.project.rpc.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import xyz.mwszksnmdys.common.service.InnerUserInterfaceInfoService;
import xyz.mwszksnmdys.project.common.ErrorCode;
import xyz.mwszksnmdys.project.exception.BusinessException;
import xyz.mwszksnmdys.project.mapper.UserInterfaceInfoMapper;
import xyz.mwszksnmdys.project.model.entity.UserInterfaceInfo;

@DubboService
@Slf4j
public class RpcUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements InnerUserInterfaceInfoService {


    /**
     * 是否还有调用次数
     *
     * @param userId          用户id
     * @param interfaceInfoId 接口id
     * @return boolean
     */
    @Override
    public boolean hasInvokeNum(long userId, long interfaceInfoId) {
        return false;
    }

    /**
     * 根据userId、interfaceInfoId计数
     *
     * @param userId          用户id
     * @param interfaceInfoId 接口id
     * @return boolean
     */
    @Override
    public boolean invokeInterfaceCount(long userId, long interfaceInfoId) {
        long start = System.currentTimeMillis();
        if (interfaceInfoId < 0 || userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaUpdateWrapper<UserInterfaceInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId);
        wrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        boolean update = this.update(wrapper);
        long end = System.currentTimeMillis();
        log.info("调用时间{}", end - start);
        return update;
    }
}
