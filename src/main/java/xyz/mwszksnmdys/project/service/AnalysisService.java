package xyz.mwszksnmdys.project.service;

import xyz.mwszksnmdys.project.model.vo.InterfaceInfoVO;

import java.util.List;

public interface AnalysisService {
    List<InterfaceInfoVO> getTopInvokedInterfaceInfo(int topN);
}
