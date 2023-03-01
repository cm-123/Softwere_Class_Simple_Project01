package com.project.sys.service;

import com.project.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenyin
 * @since 2023-02-27
 */
public interface IUserService extends IService<User> {


    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);
}
