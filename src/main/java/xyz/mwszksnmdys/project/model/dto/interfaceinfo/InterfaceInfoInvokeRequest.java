package xyz.mwszksnmdys.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用请求
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;
    private String userRequestParams;


    private static final long serialVersionUID = 1L;
}