package com.project.sys.mapper;

import com.project.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenyin
 * @since 2023-02-27
 */
public interface UserMapper extends BaseMapper<User> {

    List<String> getRoleNameByUserId(Integer userId);
}
