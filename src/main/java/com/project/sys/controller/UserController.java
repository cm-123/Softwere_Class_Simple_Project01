package com.project.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.vo.Result;
import com.project.sys.entity.User;
import com.project.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashMap;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/list")
    public Result<?> getUserListPage(@RequestParam(value = "username",required = false) String username,
                                     @RequestParam(value = "phone",required = false)String phone,
                                     @RequestParam(value = "pageNo",required = false)Long pageNo,
                                     @RequestParam(value = "pageSize",required = false)Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(username != null,User::getUsername,username);
        wrapper.eq(phone != null,User::getPhone,phone);
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo, pageSize);
        iUserService.page(page,wrapper);

        Map<String ,Object> data =  new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());


        return Result.success(data);

    }


    @PostMapping
    public  Result<?> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        iUserService.save(user);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public  Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        iUserService.updateById(user);
        return Result.success("修改用户数据成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = iUserService.getById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteUserById(@PathVariable("id") Integer id){
        iUserService.removeById(id);
        return Result.success("删除用户成功");
    }

}


