package com.project.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.sys.entity.User;
import com.project.sys.mapper.UserMapper;
import com.project.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenyin
 * @since 2023-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {
        //登录逻辑界面

        //根据用户名和密码查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        queryWrapper.eq(User::getPassword, user.getPassword());
        User loginUser = this.baseMapper.selectOne(queryWrapper);
        //结果不为空,则生成token ,将用户信息存放在redis
        if (loginUser != null) {
            //暂时用UUID,终极方案jwt
            String key = "user:" + UUID.randomUUID();


        //存入redisloginUser.setPassword(null);
        redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);


        //返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", key);
        return data;
        }

        return null;

    }
}
