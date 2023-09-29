package xyz.mwszksnmdys.project.rpc.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import xyz.mwszksnmdys.common.service.InnerUserInterfaceInfoService;
import xyz.mwszksnmdys.project.common.ErrorCode;
import xyz.mwszksnmdys.project.exception.BusinessException;
import xyz.mwszksnmdys.project.mapper.UserInterfaceInfoMapper;
import xyz.mwszksnmdys.project.model.entity.UserInterfaceInfo;
import xyz.mwszksnmdys.project.service.UserInterfaceInfoService;

import javax.annotation.Resource;

@DubboService
@Slf4j
public class RpcUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 是否还有调用次数
     *
     * @param userId          用户id
     * @param interfaceInfoId 接口id
     * @return boolean
     */
    @Override
    public boolean hasInvokeNum(long userId, long interfaceInfoId) {
        if (userId <= 0 || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getUserId, userId)
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .gt(UserInterfaceInfo::getLeftNum, 0);

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        return userInterfaceInfo != null;
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
        return userInterfaceInfoService.invokeInterfaceCount(interfaceInfoId, userId);
    }
}
