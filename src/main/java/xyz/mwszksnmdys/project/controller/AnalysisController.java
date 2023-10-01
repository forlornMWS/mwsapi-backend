package xyz.mwszksnmdys.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mwszksnmdys.common.common.BaseResponse;
import xyz.mwszksnmdys.common.common.ResultUtils;
import xyz.mwszksnmdys.project.annotation.AuthCheck;
import xyz.mwszksnmdys.project.model.vo.InterfaceInfoVO;
import xyz.mwszksnmdys.project.service.AnalysisService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private AnalysisService analysisService;

    @GetMapping("/top/interface/invoke/{topN}")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> getTopInvokedInterfaceInfo(@PathVariable String topN) {
        List<InterfaceInfoVO> topInvokedInterfaceInfo = analysisService.getTopInvokedInterfaceInfo(Integer.parseInt(topN));
        return ResultUtils.success(topInvokedInterfaceInfo);
    }
}
