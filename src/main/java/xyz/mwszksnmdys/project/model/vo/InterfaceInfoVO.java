package xyz.mwszksnmdys.project.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mwszksnmdys.project.model.entity.InterfaceInfo;

@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {
    /**
     * 调用次数
     */
    private Integer invokeNum;
}


