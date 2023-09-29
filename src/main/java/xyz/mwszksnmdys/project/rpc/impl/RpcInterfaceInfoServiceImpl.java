package xyz.mwszksnmdys.project.rpc.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import xyz.mwszksnmdys.common.domain.InterfaceInfoDO;
import xyz.mwszksnmdys.common.service.InnerInterfaceInfoService;
import xyz.mwszksnmdys.common.common.ErrorCode;
import xyz.mwszksnmdys.common.exception.BusinessException;
import xyz.mwszksnmdys.project.mapper.InterfaceInfoMapper;
import xyz.mwszksnmdys.project.model.entity.InterfaceInfo;

import javax.annotation.Resource;

@DubboService
public class RpcInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;


    /**
     * 根据path、method查询接口信息
     *
     * @param path   请求路径
     * @param method 请求方法
     * @return InterfaceInfo
     */
    @Override
    public InterfaceInfoDO getInvokeInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceInfo::getUrl, path);
        wrapper.eq(InterfaceInfo::getMethod, method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(wrapper);
        InterfaceInfoDO target = new InterfaceInfoDO();
        if (interfaceInfo != null) {
            BeanUtils.copyProperties(interfaceInfo, target);
        }
        return target;
    }
}
