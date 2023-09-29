package xyz.mwszksnmdys.project.rpc.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import xyz.mwszksnmdys.common.domain.UserDO;
import xyz.mwszksnmdys.common.service.InnerUserService;
import xyz.mwszksnmdys.project.common.ErrorCode;
import xyz.mwszksnmdys.project.exception.BusinessException;
import xyz.mwszksnmdys.project.mapper.UserMapper;
import xyz.mwszksnmdys.project.model.entity.User;

import javax.annotation.Resource;

@DubboService
public class RpcUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据accessKey查询用户
     *
     * @param accessKey accessKey
     * @return User
     */
    @Override
    public UserDO getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccessKey, accessKey);
        User user = userMapper.selectOne(wrapper);
        UserDO target = new UserDO();
        if (user != null) {
            BeanUtils.copyProperties(user, target);
        }
        return target;
    }
}
