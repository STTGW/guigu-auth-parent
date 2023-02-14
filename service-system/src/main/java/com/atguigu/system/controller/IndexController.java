package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.common.utils.JwtHelper;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import com.sun.prism.shader.Mask_TextureSuper_AlphaTest_Loader;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户登录的接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    //login
    //{"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        SysUser sysUser = sysUserService.getUserInfoByUserName(loginVo.getUsername());
        if(sysUser == null ){
            throw new GuiguException(20001,"用户不存在");
        }

        String password = loginVo.getPassword();
        String md5 = MD5.encrypt(password);
        if(!sysUser.getPassword().equals(md5)){
            throw  new GuiguException(20001,"密码不正确");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw  new GuiguException(20001,"用户不可用");
        }

        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    //info
    //{"code":20000,"data":{"roles":["admin"],
    // "introduction":"I am a super administrator",
    // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
    // "name":"Super Admin"}}
    @GetMapping("info")
    public Result info(HttpServletRequest request){
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        Map<String,Object> map =  sysUserService.getUserInfo(username);


//        map.put("roles","[admin]");
//        map.put("introduction","I am a super administrator");
//        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//        map.put("name","Super Admin ggggggggg");
        return Result.ok(map);

    }
}
