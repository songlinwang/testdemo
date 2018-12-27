package example.demo.mapper;

import example.demo.meta.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author wsl
 * @date 2018/11/23
 */
public interface UserMapper {

    User getUserById(@Param("ids") int id);
}
