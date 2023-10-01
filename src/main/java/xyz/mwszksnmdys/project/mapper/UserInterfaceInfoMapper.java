package xyz.mwszksnmdys.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.mwszksnmdys.project.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author mwszksnmdys
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> getTopInvokedInterfaceInfo(@Param("limit") int limit);
}




