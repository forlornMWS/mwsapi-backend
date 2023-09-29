package xyz.mwszksnmdys.project.model.dto.userinterfaceinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mwszksnmdys.common.common.PageRequest;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author mws
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 调用用户Id
     */
    private Long userId;

    /**
     * 接口Id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常 ，1-禁用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}