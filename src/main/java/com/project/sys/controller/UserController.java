package com.project.sys.controller;

import com.project.common.vo.Result;
import com.project.sys.entity.User;
import com.project.sys.service.IUserService;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenyin
 * @since 2023-02-27
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = iUserService.list();
        return Result.success(list);
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
         Map<String,Object> data = iUserService.login(user);
         if (data != null){
             return Result.success(data);
         }
        return Result.fail(20002,"用户名或者密码错误");
    }

}
