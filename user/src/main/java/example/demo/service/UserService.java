package example.demo.service;

import example.demo.cache.GetCache;
import example.demo.mapper.UserMapper;
import example.demo.meta.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wsl
 * @date 2018/11/23
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @GetCache(id = "getUserById",expiry = 1,timeUnit = TimeUnit.MINUTES)
    public User getUserById(int id){
        return userMapper.getUserById(id);
    }
}
