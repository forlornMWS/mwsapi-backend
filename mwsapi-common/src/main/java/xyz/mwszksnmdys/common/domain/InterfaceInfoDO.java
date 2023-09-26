package xyz.mwszksnmdys.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 */
@Data
public class InterfaceInfoDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 请求参数
     * [
     *   {
     *     "name": "salt",
     *     "type": "string"
     *   },
     *   {
     *     "name": "str",
     *     "type": "string"
     *   }
     * ]
     */
    private String requestParams;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDelete;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}