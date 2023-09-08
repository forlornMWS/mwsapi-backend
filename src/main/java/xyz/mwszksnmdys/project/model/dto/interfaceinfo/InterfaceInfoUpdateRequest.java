package xyz.mwszksnmdys.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;
    private String requestParams;


    /**
     * 名称
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态(0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;


    private static final long serialVersionUID = 1L;
}