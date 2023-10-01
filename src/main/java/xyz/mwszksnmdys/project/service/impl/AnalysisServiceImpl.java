package xyz.mwszksnmdys.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.mwszksnmdys.project.mapper.UserInterfaceInfoMapper;
import xyz.mwszksnmdys.project.model.entity.InterfaceInfo;
import xyz.mwszksnmdys.project.model.entity.UserInterfaceInfo;
import xyz.mwszksnmdys.project.model.vo.InterfaceInfoVO;
import xyz.mwszksnmdys.project.service.AnalysisService;
import xyz.mwszksnmdys.project.service.InterfaceInfoService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;


    @Override
    public List<InterfaceInfoVO> getTopInvokedInterfaceInfo(int topN) {
        List<UserInterfaceInfo> topInvokedInterfaceInfo = userInterfaceInfoMapper.getTopInvokedInterfaceInfo(topN);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = topInvokedInterfaceInfo.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        return list.stream().map(item -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(item, interfaceInfoVO);
            int invokeNum = interfaceInfoIdObjMap.get(item.getId()).get(0).getTotalNum();
            interfaceInfoVO.setInvokeNum(invokeNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
    }
}
