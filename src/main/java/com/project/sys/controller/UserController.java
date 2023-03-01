package com.project.sys.controller;

import com.project.common.vo.Result;
import com.project.sys.entity.User;
import com.project.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

/*
* @CrossOrigin
* 这个注解可以解决跨域问题
* 问题描述::8888/#/login?redirect=%2Fdashboard:1
* Access to XMLHttpRequest at 'http://localhost:9999/sys/user/login' from origin 'http://localhost:8888' has been blocked by CORS policy: N
* o 'Access-Control-Allow-Origin' header is present on the requested resource.
* request.js?b775:75 errError: Network Errorxhr.js?b50d:160
* POST http://localhost:9999/sys/user/login net::ERR_FAILED 200
* */


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


    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestParam("token") String token){
        Map<String ,Object> data = iUserService.getUserInfo(token);
        if (data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"用户信息获取失败");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        iUserService.logout(token);
        return Result.success("注销成功");
    }


}


